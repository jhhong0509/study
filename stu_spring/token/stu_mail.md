# 이메일 발송에 대한 공부
### yml 설정
``` yml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}
    properties:
      mail:
        smtp:
          auth: true
          timeout: 5000
          starttls:
            enable: true
```
- host - gmail을 사용할 것이기 때문에 gmail로 설정한다.
- port - SSL이 요구되면 465, TLS가 요구되면 587로 설정해야 한다.
- username - 이메일 계정
- password - 이메일 비밀번호
- timeout - 커넥션 타임아웃 시간 설정
- starttls - SSL 사용 여부

### MailServiceImpl

``` java
@Async
@Override
public void sendMail(String sendTo) {
    try {
        final MimeMessagePreparator preparator = mimeMessage -> {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(sender); // 보낸 사람 설정
            helper.setTo(sendTo); // 받을 사람 설정
            helper.setSubject("공부용 알림"); // 메일 제목
            helper.setText("공부용 알림 내용"); // 알림 내용
        };
        javaMailSender.send(preparator);
    } catch (Exception e) {
        System.out.println(e.getCause());
        System.out.println(e.getMessage());
        throw new EmailSendException();
    }
}
```

