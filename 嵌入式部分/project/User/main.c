


#include "stm32f10x.h"
#include <stdio.h>
#include "OLED.h"
#include "stm32f10x.h"                  // Device header
#include "Delay.h"
#include "OLED.h"
#include "Servo.h"
#include "Key.h"
#include "Serial.h"

// 定义超声波引脚
#define TRIG_PIN GPIO_Pin_2
#define ECHO_PIN GPIO_Pin_3
#define TRIG_PORT GPIOA
#define ECHO_PORT GPIOA

uint8_t RxData;			//定义用于接收串口数据的变量
uint8_t open = 0x31;
uint8_t close = 0x30;
uint8_t KeyNum;			//定义用于接收键码的变量
float Angle;			//定义角度变量
float save_dis=100000;
int tig_messag = 0;

// 定义时钟周期函数
void delay_us(uint32_t us) {
    us *= (SystemCoreClock / 1000000) / 5;
    while (us--) {
        __asm("nop");
    }
}

// 初始化函数
void init(void) {
    GPIO_InitTypeDef GPIO_InitStruct;

    // 启动时钟
    RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA, ENABLE);

    // 配置TRIG引脚为输出
    GPIO_InitStruct.GPIO_Pin = TRIG_PIN;
    GPIO_InitStruct.GPIO_Mode = GPIO_Mode_Out_PP;
    GPIO_InitStruct.GPIO_Speed = GPIO_Speed_50MHz;
    GPIO_Init(TRIG_PORT, &GPIO_InitStruct);

    // 配置ECHO引脚为输入
    GPIO_InitStruct.GPIO_Pin = ECHO_PIN;
    GPIO_InitStruct.GPIO_Mode = GPIO_Mode_IPD; // 输入上拉
    GPIO_Init(ECHO_PORT, &GPIO_InitStruct);
}

// 发送超声波脉冲
void send_pulse(void) {
    GPIO_SetBits(TRIG_PORT, TRIG_PIN);
    delay_us(10);
    GPIO_ResetBits(TRIG_PORT, TRIG_PIN);
}

// 读取脉冲宽度并转换为距离
float read_distance(void) {
    uint32_t pulse_width = 0;
    while (!GPIO_ReadInputDataBit(ECHO_PORT, ECHO_PIN)); // 等待高电平
    while (GPIO_ReadInputDataBit(ECHO_PORT, ECHO_PIN)) {
        pulse_width++; // 计算高电平持续时间
        delay_us(1);
    }
    // 将脉冲宽度转换为距离（单位：厘米）
    return pulse_width / 58.0;
}

int main(void) {
    float distance;
	OLED_Init();
    
    // 初始化
    init();
	//OLED_ShowString(1, 1, "Distance:");
	OLED_ShowString(2, 1, "000.0000 CM");
	OLED_Init();		//OLED初始化
	Servo_Init();		//舵机初始化
	Key_Init();			//按键初始化
	Serial_Init();
    int coun = 0;
    while (1) {
		if(coun <10000000){
			// 发送超声波脉冲
			send_pulse();
			
			// 读取脉冲宽度并转换为距离
			distance = read_distance();
        
			// 延迟一段时间
        
			OLED_ShowNum(2, 1, (int)distance, 10);
			save_dis = distance;
			coun=0;
		}
		coun++;
        
		//OLED_ShowNum(2, 5, HCSR04_distance.DistanceFloat, 4);
        // 处理距离数据
        //printf("Distance: %.2f cm\n", distance);
		KeyNum = Key_GetNum();
		if (KeyNum == 1)				//按键1按下
		{
			if(tig_messag == 0){
				tig_messag = 1;
			}else{
				tig_messag = 0;
			}
			
			Angle += 90;				//角度变量自增30
			if (Angle > 90)			//角度变量超过180后
			{
				Angle = 0;				//角度变量归零
			}
			//Servo_SetAngle(Angle);			//设置舵机的角度为角度变量
			if(Angle == 90){
				OLED_ShowString(1,1,"open");
			}else{
				OLED_ShowString(1,1,"close");
			}
			//continue;
		}
		if(Angle == 0){
				if( save_dis >10){
					Servo_SetAngle(Angle);
				}
		}else{
			Servo_SetAngle(Angle);
		}
		
		if(tig_messag == 1){
			continue;
		}
		//Servo_SetAngle(Angle);
		//delay_us(10000); // 延迟100毫秒
		//Serial_Init();
		if (Serial_GetRxFlag() == 1)			//检查串口接收数据的标志位
		{
			RxData = Serial_GetRxData();		//获取串口接收的数据
			//OLED_ShowNum(3,1,RxData,3);
			if(RxData == open){
				//OLED_ShowNum(4,1,666,3);
				Angle = 90;
				OLED_ShowString(1,1,"open");
			}
			if(RxData == close){
				OLED_ShowString(1,1,"close");
				Angle = 0;
			}
			//Servo_SetAngle(Angle);			//设置舵机的角度为角度变量
			//delay_us(1000000);
			Serial_SendByte(RxData);			//串口将收到的数据回传回去，用于测试
			OLED_ShowHexNum(3, 1, RxData, 2);	//显示串口接收的数据
		}
    }
}
