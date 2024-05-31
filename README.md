## Background Introduction

- The purpose of this project is to adapt to the school’s engineering practice topic: the parking lot system. More importantly, it aims to enhance our understanding and practical abilities.

- The current functionalities of the project are as follows: implementing license plate recognition and positioning for a single vehicle in a parking lot environment.

## Model Selection

### License Plate Localization Model

Considering the need to locate only one license plate and having found the CCPD dataset during our search, we chose the wR2 model mentioned in the CCPD dataset [here](https://github.com/detectRecog/CCPD?tab=readme-ov-file).

### License Plate Classification Model

Since this is a binary classification problem, we defined a relatively simple convolutional neural network (CNN) for binary classification.

### License Plate Recognition

Given that new energy vehicle license plates have eight characters while regular plates have seven, we adopted the LPRnet structure with some modifications. This approach covers both types of plates.

Modifications are as follows:

- Considering the small input image size of the original model, we modified the input size and added a simple convolutional layer at the beginning. This allows for slightly larger and clearer images to improve accuracy.
- Note: To prevent minor deviations in license plate localization from affecting recognition performance, we enlarged the bounding box after locating the license plate. This also incorporates the improvements to LPRnet mentioned in the related research.

## Dataset Selection

### License Plate Localization Model Dataset

We used the CCPD dataset, with outputs being the coordinates of the top-left and bottom-right corners of the license plate.

### License Plate Classification Model Dataset

We used a dataset of non-license plate images that we collected and a set of license plate images extracted from the CCPD dataset with enlarged bounding boxes.

### License Plate Recognition Dataset

We used the dataset of license plate images extracted from the CCPD dataset with enlarged bounding boxes.

## Model Limitations

Due to the scarcity of new energy vehicle plates in the dataset, the model performs very well on regular plates but is less accurate on new energy plates. If a larger dataset of new energy vehicle plates is added, the overall performance should improve.

## Project Main Content

- Frontend and backend modules
- Demonstration video
- Three pre-trained models
- Two demonstration images
- Comprehensive code (including the following):
  - Model definition and loading
  - Integration of the three models
  - Real-time display using camera input (approximately 15 FPS in CPU environment, up to 30 FPS in GPU environment)

## Python Environment

python: '3.8.18 (default, Sep 11 2023, 13:39:12) [MSC v.1916 64 bit (AMD64)]'
torch: 2.1.2+cu121
Other library versions are shown in the code.

## Update 1

- Added ultrasonic radar to prevent the barrier from hitting the car.
- Integrated with the parking lot backend system.
- Added payment operations. As this is a course project and lacks enterprise certification, the QR code payment operation calls Alipay’s simulation API, which functions similarly to real payments.
- For system members, the backend automatically deducts fees without needing to scan a code.
- Further optimized functionality logic.

# Appendix

## Wiring Logic

- Development board: stm32103c8t6
- Servo motor:
  - GND -- Ground
  - 5V -- Power
  - PWM -- A1
- Ultrasonic sensor (HC-SR04):
  - VCC -- Power
  - GND -- Ground
  - ECHO -- A2
  - TRIG -- A3
- USB to Serial Converter:
  - GND -- Ground
  - RXD -- A9
  - TXD -- A10
- OLED Display:
  - GND -- Ground
  - VCC -- Power
  - SCL -- B8
  - SDA -- B9

## Main Members and Contributions

- Changming Chen
  - Model design and training
  - Embedded system design and implementation
  - Overall logic integration
- Guolong Zhong
  - Web frontend and backend design and implementation
