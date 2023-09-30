package apiDocuments;

import java.io.IOException;
import org.apache.http.client.fluent.*;

public class makeAbstractRequest
{
    public static void main(String[] args) {
        makeAbstractRequest();
    }

    private static void makeAbstractRequest() {

        try {

            Content content = Request.Get('https://emailvalidation.abstractapi.com/v1/?api_key=d9db538de48c4a14bf95c6cfa7a45824&email=yoohamj@gmail.com')

                    .execute().returnContent();

            System.out.println(content);
        }
        catch (IOException error) { System.out.println(error); }
    }
}