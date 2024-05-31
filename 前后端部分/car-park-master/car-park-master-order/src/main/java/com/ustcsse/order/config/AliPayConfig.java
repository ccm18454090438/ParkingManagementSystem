package com.ustcsse.order.config;

import com.alipay.easysdk.kernel.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: AliPayConfig
 * Package: com.ustcsse.order
 * Description:
 *
 * @Author CoderMountain
 * @Create 2024/4/10 16:15
 * @Version 1.0
 */
@Configuration
public class AliPayConfig {

    @Bean
    public Config config(){
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi-sandbox.dl.alipaydev.com";
        config.signType = "RSA2";
        config.appId = "9021000136603098";
        config.merchantPrivateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCFxenNdBUPQTlK86ZNeWSR0C0VMr2RdBCrHMt+Fyfx5Mgp46NkgBAOVy74hy+lNCYHDh4EdHFt9fxNXo5ScmNv183YTjLnOvTULbNywHks7VrZV0EYrnKXje7oFuXKsEeIcP8re2LBis+G7kR1pKr+kKHNwkMjAdOp2+W+EZIHdVvy0eIF0zvDUk6PzbqoIANq/2WXVd+HrSZZpjxneZC7dlnhJy59/2NLFLyDY3TXsRObzkbYO+Znkxqnl7/ROwj7BChbDwF46Ytq0XKSl/TkkzvDnUomEZyvwmpC3eaHCZcxsh3Wl0inSJ8RvT5h3Zd7CE8ujl79HYDF8eICc7tXAgMBAAECggEAawLZd1snBZ79f/HM1SkIxIQtrEl80ABy3Q8Z0fIgKCcF4EyThm/TaEVh87ng6V0AFqC6boHfw0EF5baAT3/l/7A4oJJdsg+9joZIiMFjrgbpp/dewam7TXVi6hJ3HF9Lud/6oAQCCiVKwfuUlC94AlAcv4ZpQnXRpo2cCSHxGTkjDvSmogsSCrdNgpNqLkmDew+w5StBx/cKRaWryL9wajhzdUtfw8ES8FzuwXLAxZwE+FBYOYKFDgmmo5lna3yXrP7Y1+nRowCOwdCb1btg4K/qMwZ0fUEeSO40cfpyCyOW63XkO1vb6RwClUu2zSiqWgqkutsaMaV6PKrPhfjm8QKBgQDX306ejLdkvwq/2goub2QHxqfuYKngSXISb525IxkboqLmt+IOYNIYihsiHjhpgkA/eKJNUfVR7FT02kRtRcqXv115rc3nxT/7qsEKxWznP5HA2NxnVQr1jszJf6U7UfcIjtY9AgWmBtzrS9eGJO4ctgJCUtqRDpYl+EwJBllYrQKBgQCeo8KZWmpKe6ACIv5ilImevlpbTrcitTAteAYGcaWMFpDWJvENpAOWUTY8U2fqZAmKAD3amIjmLqm37UK0iypsUplXsh1WxniTYaXoSH+5CkRfuTTkAmfRiHpuW82kN38Mi8SMZVoAR32V5ttMeV74591IViQwJ++M36bIwmcQkwKBgFQsHGmyEKhV3YklKaclg+mMSlqmklaEDNincOaJiva9matAtAJLOmCbKS1Kf1CUmxykmxRvCqrg7ddzbA73kaFN2tLhNFJVFTe6Qv9CL86TeO+rH3WjMVzCS5OeE/1nXl2V5G9xZGZycp3Eusrxh27mgUb/K344I8+2W1+BfEEZAoGABwKVhtFIASKVX4LMWu7VZf5jX8WKsi3vgxLasU40UoxsugnefQvSXMIZ83B8Fw90nHSGRzKWWhWVylrhp7i+lyrPO+DTCxKaIWkfO7R4cGTTwKcefzTcAZZWUqob3DXXl2ZVuR6Ba6G9GvKonih+2eDlFl04HCinnUIEzLpKc6ECgYB6rm1ylEhQdvXHWv5qbJkQxYfQcto6dLMJhzZ3ZkpAlQhIP4oeXVJfcfAyqCMxSIoBcRvl/bheSPYCpzK1AhBLz6au6WIBNYsECDDAJ1kA9t1PM+YBbvvXMNZ0cbk+k3ULxhEvyi/KS2tlS+MFfsEjfVaP+Ku8gvDyzOjWP0mBPw==";
        config.alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2R0hXIynrBj3Nn64sOOaIe0WDa5sj0VuHrwSnk3Rc7Si5VSmX97n+dGmDXkmR8WCCFle3R5KWn4Fvaieog62hK9txqCEH0XBVorzPD8hELRyk+hquu9SkYw/xSyg+s5AXZeye4Xj/8dgw0ao4Jj1feTSjcSMes0GNrl89bk5bDh7RRN17CXInOtOg2frm7+FP+8CvmO99m9ePJ27ya7XslHz832/cY8aELdu3drv4U1XEMTeDGzfn68zuOKE44VhnDtRwR9YR9jzxL41Mhv1mFW5rQ8uQWstFkZ7OxxxOqYU8IGHauWCstKAJ0CYevV7qlvhHyK2xBdiB0+AlOYhxQIDAQAB";
        config.notifyUrl = "http://evh4cy.natappfree.cc/order/notify";
        config.encryptKey = "";
        return config;
    }


}
