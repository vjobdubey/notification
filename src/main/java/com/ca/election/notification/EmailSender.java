package com.ca.election.notification;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class EmailSender {

    public static void main(String[] args) {

        // SMTP server configuration
        final String smtpHost = "smtp.gmail.com";
        final int smtpPort = 587;
        final //  final String toEmail = "ankur.goyal@wissen.com, vjobdubey@gmail.com,vijay.dubey1@wissen.com, Ashutosh.Gattani@wissen.com, Mandar.Kasangottuwar@wissen.com, AnshuKumar.Jha@wissen.com, Sakthivel.Chandran@wissen.com";
        String username = "vjobdubey@gmail.com"; // Sender email
        final String password = "ykcf xici dayr qldk";    // App password, NOT your Gmail password

        // Recipient
      //  final String toEmail = "ankur.goyal@wissen.com, vjobdubey@gmail.com,vijay.dubey1@wissen.com, Ashutosh.Gattani@wissen.com, Mandar.Kasangottuwar@wissen.com, AnshuKumar.Jha@wissen.com, Sakthivel.Chandran@wissen.com";

        final String toEmail = "vjobdubey@gmail.com,vijay.dubey1@wissen.com";
        //macq.ca@wissen.com

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
                      <meta charset="UTF-8">
                      <style>
                      
                      
                      /* 1. Use a more-intuitive box-sizing model */
                       *, *::before, *::after {
                         box-sizing: border-box;
                       }
                    
                       /* 2. Remove default margin */
                       * {
                         margin: 0;
                       }
                    
                       /* 3. Enable keyword animations */
                       @media (prefers-reduced-motion: no-preference) {
                         html {
                           interpolate-size: allow-keywords;
                         }
                       }
                    
                       body {
                         /* 4. Add accessible line-height */
                         line-height: 1.5;
                         /* 5. Improve text rendering */
                         -webkit-font-smoothing: antialiased;
                       }
                    
                       /* 6. Improve media defaults */
                       img, picture, video, canvas, svg {
                         display: block;
                         max-width: 100%;
                       }
                    
                       /* 7. Inherit fonts for form controls */
                       input, button, textarea, select {
                         font: inherit;
                       }
                    
                       /* 8. Avoid text overflows */
                       p, h1, h2, h3, h4, h5, h6 {
                         overflow-wrap: break-word;
                       }
                    
                       /* 9. Improve line wrapping */
                       p {
                         text-wrap: pretty;
                       }
                       h1, h2, h3, h4, h5, h6 {
                         text-wrap: balance;
                       }
                    
                       /*
                         10. Create a root stacking context
                       */
                       #root, #__next {
                         isolation: isolate;
                       }
                      
                      
                      
                      
                      
                      
                        body {
                          font-family: Arial, sans-serif;
                          color: #333;
                        }
                        .container {
                          width: 90%;
                          padding: 20px;
                        }
                        h2 {
                          color: #005baa;
                        }
                        table {
                          width: 100%;
                          border-collapse: collapse;
                          margin-top: 15px;
                        }
                        th, td {
                          text-align: left;
                          padding: 8px 12px;
                          border: 1px solid #ddd;
                        }
                        th {
                          background-color: #f5f5f5;
                        }
                        .options {
                          margin-top: 20px;
                        }
                        .option-box {
                          border: 1px solid #ccc;
                          padding: 12px;
                          margin-bottom: 10px;
                          background-color: #f9f9f9;
                        }
                        .option-title {
                          font-weight: bold;
                          color: #333;
                        }
                        .footer {
                          margin-top: 30px;
                          font-size: 13px;
                          color: #555;
                        }
                    
                    	 .btn {
                        display: inline-block;
                        background-color: #005baa;
                        color: white;
                        padding: 10px 20px;
                        margin-top: 10px;
                        text-decoration: none;
                        border-radius: 5px;
                        font-weight: bold;
                        font-size: 14px;
                    	width: 100px;
                    	text-align: center;
                      }
                    
                      .btn:hover {
                        background-color: #003f7d;
                      }
                    
                      .notification {
                          background-color: #fff4d6;
                          border-radius: 6px;
                          padding: 15px 20px;
                          border: 1px solid #ffe8aa;
                          margin-bottom: 20px;
                          color: #7b5a00;
                          font-size: 14px;
                        }
                    
                        .notification strong {
                          color: #aa8600;
                        }
                        
                        .custom-btn {
                          background-color: #005baa; /* Deep blue */
                          color: white;
                          border: none;
                          padding: 10px 20px;
                          margin: 8px 5px;
                          font-size: 16px;
                          border-radius: 6px;
                          cursor: pointer;
                          text-align: center;
                          transition: background-color 0.3s ease;
                        }
                    
                        .custom-btn:hover {
                          background-color: #004080; /* Darker blue on hover */
                        }
                      </style>
                    </head>
                    <body>
                      <div class="container">
                          <div>
                           Dear Shareholder(s), <br/> <br/>
                    
                            <b>Subject:</b> Action Required – Corporate Action Election for Dividend Option<br/><br/>
                             We are pleased to inform you that <b>CHINA EDUCATION GROUP HOLDIN</b> has declared a dividend of <b>HK$8.35</b> per equity share (face value HK$2) for the Financial Year 2024-25 during its 29th Annual General Meeting held on 23rd July 2025.<br/><br/>
                    
                              The eligible dividend amount, after applicable TDS deduction, has been credited to your registered bank account. 
                            <div>   
                            <br/>       
                    	  <div class="notification">
                    		<strong>Important:</strong> This corporate action notice requires your response by the deadline specified above.
                    		If no instruction is received, the default option (Option 1 – Cash Dividend) will be automatically applied to your position.
                    		Please ensure all quantities do not exceed your total position of <strong>7,055,000 shares</strong>.
                          </div>
                          
                    
                        <table>
                          <tr><th>Name</th><td>CHINA EDUCATION GROUP HOLDIN</td></tr>
                          <tr><th>Security Code</th><td>0839.HK</td></tr>
                          <tr><th>Event Type</th><td>Dividend Option</td></tr>
                          <tr><th>ISIN Code</th><td>KYG2163M1033</td></tr>
                          <tr><th>Our Deadline</th><td>14-Mar-2025 4:00 PM Hong Kong Time</td></tr>
                          <tr><th>Ex Date</th><td>19-Feb-2025</td></tr>
                          <tr><th>Record Date</th><td>20-Feb-2025</td></tr>
                          <tr><th>Pay Date</th><td>26-Mar-2025</td></tr>
                          <tr><th>Position Type</th><td>SBL_Borrow</td></tr>
                          <tr><th>Declared Cash Rate</th><td>HKD 0.1112</td></tr>
                          <tr><th>Reinvestment Price</th><td>HKD 2.456</td></tr>
                          <tr><th>Your Position as at 20-Feb-2025</th><td>TIMBER HILL (EUROPE) AG - 7,055,000 shares</td></tr>
                        </table>
                    
                        <br>
                    
                    
                     
                     <a href="mailto:macq.ca@wissen.com?subject=Corporate Action Election - CHINA EDUCATION GROUP HOLDIN&body=Corporate Action Details:%0A- Company: CHINA EDUCATION GROUP HOLDIN%0A- Security Code: 0839.HK%0A- Event Type: Dividend Option%0A- ISIN Code: KYG2163M1033%0A- Deadline: 14-Mar-2025 4:00 pm Hong Kong Time%0A- Your Position: 7,055,000 shares%0A
                      Selected Option 1: Full Cash                    
                      Cash Dividend Quantity: [2000000]%0A
                      Note: Total quantities should not exceed 7,055,000 shares"
                        style="display: inline-block; padding: 10px 20px; background-color: #005baa; color: white; text-decoration: none; border-radius: 5px; font-family: sans-serif;">
                        Full Cash
                     </a>
                    
                    &nbsp; 
                     <a href="mailto:macq.ca@wissen.com?subject=Corporate Action Election - CHINA EDUCATION GROUP HOLDIN&body=Corporate Action Details:%0A- Company: CHINA EDUCATION GROUP HOLDIN%0A- Security Code: 0839.HK%0A- Event Type: Dividend Option%0A- ISIN Code: KYG2163M1033%0A- Deadline: 14-Mar-2025 4:00 pm Hong Kong Time%0A- Your Position: 7,055,000 shares%0A
                      Selected Option 2: Stock Dividend
                      Stock Dividend Quantity: [7,055,000]%0A
                     Note: Total quantities should not exceed 7,055,000 shares"
                       style="display: inline-block; padding: 10px 20px; background-color: #005baa; color: white; text-decoration: none; border-radius: 5px; font-family: sans-serif;">
                       Full Stock
                    </a>
                   &nbsp; 
                    <a href="mailto:macq.ca@wissen.com?subject=Corporate Action Election - CHINA EDUCATION GROUP HOLDIN&body=Corporate Action Details:%0A- Company: CHINA EDUCATION GROUP HOLDIN%0A- Security Code: 0839.HK%0A- Event Type: Dividend Option%0A- ISIN Code: KYG2163M1033%0A- Deadline: 14-Mar-2025 4:00 pm Hong Kong Time%0A- Your Position: 7,055,000 shares%0A%0ASelected Option: Option 3 - Split Position%0ACash Dividend Quantity: [PLEASE ENTER CASH QUANTITY]%0AStock Dividend Quantity: [PLEASE ENTER STOCK QUANTITY]%0A%0ANote: Total quantities should not exceed 7,055,000 shares"\s
                       style="display: inline-block; padding: 10px 20px; background-color: #005baa; color: white; text-decoration: none; border-radius: 5px; font-family: sans-serif;">
                       Mix Option
                    </a>
                        <div class="footer">
                          If you have any questions, please contact your relationship manager.
                        </div>
                      </div>
                      
                        <br/><br/>
                    
                         Regards,<br/>
                         Corporate Actions Team<br/>
                         <B>wissen technology</B>
                                               
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
