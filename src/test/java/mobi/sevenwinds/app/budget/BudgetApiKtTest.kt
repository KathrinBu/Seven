package mobi.sevenwinds.app.budget

import io.restassured.RestAssured
import mobi.sevenwinds.common.ServerTest
import mobi.sevenwinds.common.jsonBody
import mobi.sevenwinds.common.toResponse
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BudgetApiKtTest : ServerTest() {

    @BeforeEach
    internal fun setUp() {
        transaction { BudgetTable.deleteAll() }
    }

    @Test
    fun testBudgetPagination() {
        addRecord(BudgetRecord(2020, 5, 10, BudgetType.Приход))
        addRecord(BudgetRecord(2020, 5, 5, BudgetType.Приход))
        addRecord(BudgetRecord(2020, 5, 20, BudgetType.Приход))
        addRecord(BudgetRecord(2020, 5, 30, BudgetType.Приход))
        addRecord(BudgetRecord(2020, 5, 40, BudgetType.Приход))
        addRecord(BudgetRecord(2030, 1, 1, BudgetType.Расход))

        BudgetYearStatsResponse response = RestAssured . given ()
            .queryParam("limit", 3)
            .queryParam("offset", 1)
            .get("/budget/year/2020/stats")
            .as(BudgetYearStatsResponse.class);

        System.out.println(response.getTotal() + " / " + response.getItems() + " / " + response.getTotalByType());

        Assert.assertEquals(5, response.getTotal())
        Assert.assertEquals(3, response.getItems().size())
        Assert.assertEquals(105, response.getTotalByType().get(BudgetType.ПРИХОД.name()))
    }

    private void addRecord(BudgetRecord record)
    {
        RestAssured.given()
            .body(record)
            .post("/budget/add")
            .then()
            .statusCode(200);
    }
}

@Test
fun testStatsSortOrder() {
    addRecord(BudgetRecord(2020, 5, 100, BudgetType.Приход))
    addRecord(BudgetRecord(2020, 1, 5, BudgetType.Приход))
    addRecord(BudgetRecord(2020, 5, 50, BudgetType.Приход))
    addRecord(BudgetRecord(2020, 1, 30, BudgetType.Приход))
    addRecord(BudgetRecord(2020, 5, 400, BudgetType.Приход))

    // expected sort order - month ascending, amount descending

    BudgetYearStatsResponse response = RestAssured . given ()
        .queryParam("limit", 100)
        .queryParam("offset", 0)
        .get("/budget/year/2020/stats")
        .as(BudgetYearStatsResponse.class);

    List<BudgetRecord> items = response . getItems ();
    System.out.println(items);

    Assert.assertEquals(30, items.get(0).getAmount());
    Assert.assertEquals(5, items.get(1).getAmount());
    Assert.assertEquals(400, items.get(2).getAmount());
    Assert.assertEquals(100, items.get(3).getAmount());
    Assert.assertEquals(50, items.get(4).getAmount());
    }
private void addRecord(BudgetRecord record) {
    RestAssured.given()
        .body(record)
        .post("/budget/add")
        .then()
        .statusCode(200);
}

@Test
fun testInvalidMonthValues() {
    RestAssured.given()
        .jsonBody(BudgetRecord(2020, -5, 5, BudgetType.Приход))
        .post("/budget/add")
        .then().statusCode(400)

    RestAssured.given()
        .jsonBody(BudgetRecord(2020, 15, 5, BudgetType.Приход))
        .post("/budget/add")
        .then().statusCode(400)
}

private fun addRecord(record: BudgetRecord) {
    RestAssured.given()
        .jsonBody(record)
        .post("/budget/add")
        .toResponse<BudgetRecord>().let { response ->
            Assert.assertEquals(record, response)
        }
}
}