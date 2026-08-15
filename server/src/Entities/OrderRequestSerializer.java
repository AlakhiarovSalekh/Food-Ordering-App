package Entities;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class OrderRequestSerializer implements JsonSerializer<OrderRequest>, JsonDeserializer<OrderRequest> {
    @Override
    public JsonElement serialize(OrderRequest orderRequest, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("customerId", orderRequest.getCustomerId());
        jsonObject.addProperty("address", orderRequest.getAddress());

        JsonArray cartItemsJson = new JsonArray();
        for (Map.Entry<MenuItem, Integer> entry : orderRequest.getCartItems().entrySet()) {
            MenuItem menuItem = entry.getKey();
            int quantity = entry.getValue();
            JsonObject itemJson = new JsonObject();
            itemJson.addProperty("itemId", menuItem.getId());
            itemJson.addProperty("itemName", menuItem.getName());
            itemJson.addProperty("itemDescription", menuItem.getDescription());
            itemJson.addProperty("itemPrice", menuItem.getPrice());
            itemJson.addProperty("quantity", quantity);
            cartItemsJson.add(itemJson);
        }
        jsonObject.add("cartItems", cartItemsJson);

        return jsonObject;
    }

    @Override
    public OrderRequest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int customerId = jsonObject.get("customerId").getAsInt();
        String address = jsonObject.get("address").getAsString();

        JsonArray cartItemsJson = jsonObject.getAsJsonArray("cartItems");
        Map<MenuItem, Integer> cartItems = new HashMap<>();
        for (JsonElement element : cartItemsJson) {
            JsonObject itemJson = element.getAsJsonObject();
            int itemId = itemJson.get("itemId").getAsInt();
            String itemName = itemJson.get("itemName").getAsString();
            String itemDescription = itemJson.get("itemDescription").getAsString();
            double itemPrice = itemJson.get("itemPrice").getAsDouble();
            int quantity = itemJson.get("quantity").getAsInt();

            // Create MenuItem object using itemId, itemName, itemDescription, and itemPrice
            MenuItem menuItem = new MenuItem(itemId, itemName, itemDescription, itemPrice);
            cartItems.put(menuItem, quantity);
        }

        return new OrderRequest(customerId, address, cartItems);
    }
}
