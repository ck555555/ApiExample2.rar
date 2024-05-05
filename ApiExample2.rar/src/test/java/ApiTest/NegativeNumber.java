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


public class NegativeNumber {

@Epic("Тестируем поля с числовыми значениями")
@Feature("Negative number")
@Story("Тестируем отридцательные числа в полях id, кроме id первого уровня, который не может быть равен или менее 0")

    @Test(description = "Тестирование post запроса, проверка значеий отридцательное число")
    public void test_1_post() {
        String result = post("https://petstore.swagger.io/v2/pet",
                "{\n" +
                        "  \"id\": 30,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": -12,\n" +
                        "    \"name\": \"string\"\n" +
                        "  },\n" +
                        "  \"name\": \"doggie\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": -13,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}");


    Assert.assertEquals(result,
            "{\"id\":30,\"category\":{\"id\":-12,\"name\":\"string\"},\"name\":\"doggie\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":-13,\"name\":\"string\"}],\"status\":\"available\"}");


}

    @Test(description = "Тестирование get запроса", dependsOnMethods = "test_1_post")
    public void test_2_get() {
        String result = get("https://petstore.swagger.io/v2/pet/30");

        Assert.assertEquals(result,
                "{\"id\":30,\"category\":{\"id\":-12,\"name\":\"string\"},\"name\":\"doggie\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":-13,\"name\":\"string\"}],\"status\":\"available\"}");
        Assert.assertTrue(result.contains("name\":\"doggie"));

    }

    @Test(description = "Тестирование delete запроса", dependsOnMethods = "test_2_get")
    public void test_3_delete() {
        String result = delete("https://petstore.swagger.io/v2/pet/30");
        Assert.assertEquals(result,
                "{\"code\":200,\"type\":\"unknown\",\"message\":\"30\"}");
    }

    @Test(description = "Тестирование get-2 запроса", dependsOnMethods = "test_3_delete")
    public void test_4_get() {
        String result = get("https://petstore.swagger.io/v2/pet/30");
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
