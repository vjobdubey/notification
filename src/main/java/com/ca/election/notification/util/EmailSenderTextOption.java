package com.ca.election.notification.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailSenderTextOption {

    public static void main(String[] args) {

        // SMTP server configuration
        final String smtpHost = "smtp.gmail.com";
        final int smtpPort = 587;
        final String username = "vjobdubey@gmail.com"; // Sender email
        final String password = "ykcf xici dayr qldk";    // App password, NOT your Gmail password

        // Recipient

        //macq.ca@wissen.com

//        final String toEmail = "vjobdubey@gmail.com,vijay.dubey1@wissen.com";
       // final String toEmail = "vijay.dubey1@wissen.com, sakthisakthi380@gmail.com, Sakthivel.Chandran@wissen.com, Ashutosh.Gattani@wissen.com";

        final String toEmail = "mandar.kasangottuwar@macquarie.com, sakthivel.chandran@macquarie.com, vjobdubey@gmail.com,vijay.dubey1@wissen.com, Ashutosh.Gattani@wissen.com, Mandar.Kasangottuwar@wissen.com, AnshuKumar.Jha@wissen.com, Sakthivel.Chandran@wissen.com";
        //final String toEmail =  "vijay.dubey1@wissen.com, sakthisakthi380@gmail.com, Sakthivel.Chandran@wissen.com, Ashutosh.Gattani@wissen.com, ashok.arumugam@macquarie.com,  vincent.bedouillat@macquarie.com,  kevin.merien@macquarie.com,  tia.park@macquarie.com,  mandeep.singh3@macquarie.com";


        // Email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        // Authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );
            message.setSubject("Corporate Action Notice: CHINA EDUCATION GROUP HOLDIN");
            String htmlContent =  """
                    <!DOCTYPE html>
                    <html>
                      <head>
                        <meta charset="UTF-8" />
                       <style>
                      body {
                        font-family: 'Segoe UI', Arial, sans-serif;
                        background-color: #f9f9f9;
                        color: #333;
                        padding: 20px;
                        line-height: 1.6;
                      }
                    
                      .container {
                        background-color: #ffffff;
                        max-width: 95%;
                        margin: auto;
                        padding: 30px;
                        border-radius: 8px;
                        box-shadow: 0 0 10px rgba(0, 0, 0, 0.06);
                      }
                    
                      h2, strong {
                        color: #005baa;
                      }
                    
                      p {
                        font-size: 13px;
                        margin: 12px 0;
                      }
                    
                      table {
                        width: 100%;
                        border-collapse: collapse;
                        margin-top: 20px;
                        font-size: 13px;
                      }
                    
                      th, td {
                        padding: 6px 10px;
                        border: 1px solid #e0e0e0;
                        text-align: left;
                      }
                    
                      th {
                        background-color: #f1f4f8;
                        font-weight: 600;
                      }
                    
                      .notification {
                        background-color: #fff6d9;
                        border-left: 4px solid #ffc107;
                        padding: 12px 18px;
                        margin: 25px 0;
                        border-radius: 6px;
                        color: #795400;
                        font-size: 13px;
                      }
                    
                      .btn-group {
                        margin-top: 16px;
                        display: flex;
                        gap: 10px;
                        flex-wrap: wrap;
                      }
                    
                      .btn {
                        background-color: #005baa !important;
                        color: #fff !important;
                        padding: 6px 14px !important;
                        text-decoration: none !important;
                        border-radius: 4px !important;
                        font-size: 13px !important;
                        transition: background 0.3s ease;
                      }
                    
                      .btn:hover {
                        background-color: #00407d;
                      }
                    
                      .footer {
                        font-size: 12px;
                        color: #777;
                        margin-top: 35px;
                      }
                    </style>
                    
                      </head>
                      <body>
                        <div class="container">
                          <p>Dear Shareholder(s),</p>
                    
                          <p><strong>Subject:</strong> Action Required – Corporate Action Election for Dividend Option</p>
                    
                          <p>
                            We are pleased to inform you that <strong>CHINA EDUCATION GROUP HOLDIN</strong> has declared a dividend of
                            <strong>HKD 8.35</strong> per equity share (face value HKD 2) for the Financial Year 2024-25 during its 29th Annual General Meeting held on 23rd July 2025.
                          </p>
                    
                          <p>
                            The eligible dividend amount, after applicable tax deduction, has been credited to your registered bank account.
                          </p>
                    
                          <div class="notification">
                            <strong style="color:red">Important:</strong> This corporate action notice requires your response by the deadline specified below.
                            If no instruction is received, the default option (Option 1 – Cash Dividend) will be automatically applied.
                            <br />
                            Please ensure the total selected quantities do not exceed your position of <strong>7,055,000 shares</strong>.
                          </div>
                    
                          <table>
                            <tr><th>Company</th><td>CHINA EDUCATION GROUP HOLDIN</td></tr>
                            <tr><th>Security Code</th><td>0839.HK</td></tr>
                            <tr><th>Event Type</th><td>Dividend Option</td></tr>
                            <tr><th>ISIN</th><td>KYG2163M1033</td></tr>
                            <tr><th>Our Deadline</th><td>14-Jul-2025 4:00 PM Hong Kong Time</td></tr>
                            <tr><th>Ex Date</th><td>24-Jul-2025</td></tr>
                            <tr><th>Record Date</th><td>25-Jul-2025</td></tr>
                            <tr><th>Pay Date</th><td>26-Jul-2025</td></tr>
                            <tr><th>Position Type</th><td>SBL_Borrow</td></tr>
                            <tr><th>Declared Cash Rate</th><td>HKD 0.1112</td></tr>
                            <tr><th>Reinvestment Price</th><td>HKD 2.456</td></tr>
                            <tr><th>Your Position</th><td>TIMBER HILL (EUROPE) AG - 7,055,000 shares</td></tr>
                          </table>
                    
                            <div class="btn-group">
<a href="mailto:macq.ca@wissen.com?subject=Corporate Action Election - CHINA EDUCATION GROUP HOLDIN&body=Corporate Action Details:%0A- Company: CHINA EDUCATION GROUP HOLDIN%0A- Security Code: 0839.HK%0A- Event Type: Dividend Option%0A- ISIN Code: KYG2163M1033%0A- Deadline: 14-Mar-2025 4:00 pm Hong Kong Time%0A- Your Position: 7,055,000 shares%0A%0ASelected Option 1: Full Cash%0ACash Dividend Quantity: [2000000]%0A%0ANote: Total quantities should not exceed 7,055,000 shares"%0A
style="display: inline-block; padding: 10px 20px; background-color: #005baa; color: white; text-decoration: none; border-radius: 5px; font-family: sans-serif;">Full Cash</a>&nbsp;
<a href="mailto:macq.ca@wissen.com?subject=Corporate Action Election - CHINA EDUCATION GROUP HOLDIN&body=Corporate Action Details:%0A- Company: CHINA EDUCATION GROUP HOLDIN%0A- Security Code: 0839.HK%0A- Event Type: Dividend Option%0A- ISIN Code: KYG2163M1033%0A- Deadline: 14-Mar-2025 4:00 pm Hong Kong Time%0A- Your Position: 7,055,000 shares%0A%0ASelected Option 2: Stock Dividend%0AStock Dividend Quantity: [7,055,000]%0A%0ANote: Total quantities should not exceed 7,055,000 shares"%0A
style="display: inline-block; padding: 10px 20px; background-color: #005baa; color: white; text-decoration: none; border-radius: 5px; font-family: sans-serif;">Full Stock
</a>
&nbsp;
<a href="mailto:macq.ca@wissen.com?subject=Corporate Action Election - CHINA EDUCATION GROUP HOLDIN&body=Corporate Action Details:%0A- Company: CHINA EDUCATION GROUP HOLDIN%0A- Security Code: 0839.HK%0A- Event Type: Dividend Option%0A- ISIN Code: KYG2163M1033%0A- Deadline: 14-Mar-2025 4:00 pm Hong Kong Time%0A- Your Position: 7,055,000 shares%0A%0ASelected Option: Option 3 - Mix Option%0ACash Dividend Quantity: [PLEASE_ENTER_CASH_QUANTITY]%0AStock Dividend Quantity: [PLEASE_ENTER_STOCK_QUANTITY]%0A%0ANote: Total quantities should not exceed 7,055,000 shares"
style="display: inline-block; padding: 10px 20px; background-color: #005baa; color: white; text-decoration: none; border-radius: 5px; font-family: sans-serif;">
Mix Option
</a>
                            </div>
                            <div class="footer">
                                Note: If you have any questions or require further clarification regarding this corporate action or the dividend payment details, please contact your Relationship Manager.
                            </div>
                    
                          <p style="margin-top: 25px;">
                            Regards,<br />
                            Corporate Actions Team<br />
                            <strong>Wissen Technology</strong>
                          </p>
                        </div>
                      </body>
                    </html>                    
                    """;

            message.setContent(htmlContent, "text/html; charset=utf-8");
            // Send email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }


}
