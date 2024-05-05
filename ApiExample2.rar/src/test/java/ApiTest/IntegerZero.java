package ApiTest;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class IntegerZero {
    @Epic("Тестируем поля с числовыми значениями")
    @Feature("IntegerZero")
    @Story("Тестируем значение 0 в полях id, кроме id первого уровня, который не может быть равен или менее 0")

    @Test(description = "Тестирование post запроса, проверка значеий integer ноль")
    public void test_1_post() {
        String result = post("https://petstore.swagger.io/v2/pet",
                "{\n" +
                        "  \"id\": 0,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"string\"\n" +
                        "  },\n" +
                        "  \"name\": \"doggie\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}");

        Assert.assertTrue(result.contains("\"category\":{\"id\":0,\"name\":\"string\"}"));
        Assert.assertTrue(result.contains("\"tags\":[{\"id\":0,\"name\":\"string\"}]"));

    }

    @Test(description = "Тестирование get запроса", dependsOnMethods = "test_1_post")
    public void test_2_get() {
        String result = get("https://petstore.swagger.io/v2/pet/100");

        Assert.assertEquals(result,
                "{\"id\":100,\"category\":{\"id\":0,\"name\":\"string\"},\"name\":\"doggie\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":0,\"name\":\"string\"}],\"status\":\"available\"}");

        Assert.assertTrue(result.contains("name\":\"doggie"));


    }

    @Test(description = "Тестирование delete запроса", dependsOnMethods = "test_2_get")
    public void test_3_delete() {
        String result = delete("https://petstore.swagger.io/v2/pet/100");

        Assert.assertEquals(result,
                "{\"code\":200,\"type\":\"unknown\",\"message\":\"100\"}");
    }

    @Test(description = "Тестирование get-2 запроса", dependsOnMethods = "test_3_delete")
    public void test_4_get() {
        String result = get("https://petstore.swagger.io/v2/pet/100");


        Assert.assertEquals(result,
                null);
    }

    public String get(String url) {
        String result = null;

        try {
            Response response = Request.get(url).execute();
            result = response.returnContent().asString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result);
        return result;
    }

    public String delete(String url) {
        String result = null;

        try {
            Response response = Request.delete(url).execute();
            result = response.returnContent().asString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result);
        return result;
    }

    public String post(String url, String body) {
        String result = null;

        try {
            Response response = Request.post(url).bodyString(body, ContentType.APPLICATION_JSON).execute();
            result = response.returnContent().asString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result);
        return result;
    }
}
