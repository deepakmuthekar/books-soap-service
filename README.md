# Spring WS (SOAP) web service example

### Concepts covered 
1. Interceptor : How to log SOAP request and Response with Request Id using MDC
2. Security : Authentication with plain Username and password.
2. Custom Fault Handling : How to handle custom faults

### TODO
1. Security : Certificate based authentication

### Testing

In this example two books stored in memory map.

| ISBN in request  |
| ---------------- |
| 978-0071350938   | 
| 978-0201616224   | 

Test Users :

| Username  | Password  |
| ----------| ----------|
| user      | password  |
| deepak    | password  |

Example Request

```
 <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:book="http://deepakmuthekar.github.com/book-request">
   <soapenv:Header>
      <wsse:Security mustUnderstand="1" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
         <wsse:UsernameToken>
            <wsse:Username>deepak</wsse:Username>
            <wsse:Password>password</wsse:Password>
         </wsse:UsernameToken>
      </wsse:Security>
   </soapenv:Header>
   <soapenv:Body>
      <book:GetBookRequest>
         <book:isbn>978-0071350938</book:isbn>
      </book:GetBookRequest>
   </soapenv:Body>
</soapenv:Envelope>

```

Response
```
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header/>
   <SOAP-ENV:Body>
      <ns2:GetBookResponse xmlns:ns2="http://deepakmuthekar.github.com/book-request">
         <ns2:Book>
            <ns2:id>Inside Java Virtual Machine</ns2:id>
            <ns2:isbn>978-0071350938</ns2:isbn>
            <ns2:author>Bill Venners</ns2:author>
            <ns2:price>23.67</ns2:price>
         </ns2:Book>
      </ns2:GetBookResponse>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>

```
