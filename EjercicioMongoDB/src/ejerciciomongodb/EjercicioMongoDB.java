package ejerciciomongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import java.util.Arrays;
import java.util.regex.Pattern;
import org.bson.Document;

public class EjercicioMongoDB {

    public static void main(String[] args) {

        MongoClient mongo = new MongoClient("localhost");
        MongoDatabase db = mongo.getDatabase("ejercicio");

        MongoCollection<Document> collection = db.getCollection("restaurant");

        Document sushilito = new Document()
                .append("name", "Sushilito")
                .append("rating", 5)
                .append("categoría", Arrays.asList("sushi"));

        Document applebees = new Document()
                .append("name", "Applebee's")
                .append("rating", 5)
                .append("categoría", Arrays.asList("snack", "drink"));

        Document chiltepinos = new Document()
                .append("name", "Chiltepino's")
                .append("rating", 2)
                .append("categoría", Arrays.asList("bar", "snack"));

        Document dominospizza = new Document()
                .append("name", "Domino's Pizza")
                .append("rating", 4)
                .append("categoría", Arrays.asList("pizza"));

        Document arbolitos = new Document()
                .append("name", "Los Arbolitos")
                .append("rating", 3)
                .append("categoría", Arrays.asList("sushi"));

        Document sushisaga = new Document()
                .append("name", "Sushi Saga")
                .append("rating", 1)
                .append("categoría", Arrays.asList("sushi"));

        Document mochomos = new Document()
                .append("name", "Mochomos")
                .append("rating", 4)
                .append("categoría", Arrays.asList("marisco", "cortes", "drink"));

        Document italiannis = new Document()
                .append("name", "Italianni's")
                .append("rating", 2)
                .append("categoría", Arrays.asList("pizza", "pasta", "drink"));

        //Agregar a bd todos los restaurants
        collection.insertMany(Arrays.asList(sushilito, applebees, chiltepinos, dominospizza, arbolitos, sushisaga, mochomos, italiannis));

        //Listar todos los restaurants
        for (Document list : collection.find()) {
            System.out.println(list.toJson());
        }

        System.out.println("");

        //Checar restaurants con rating mayor a 4
        FindIterable<Document> ratings = collection.find(
                Filters.gte("rating", 4)
        );

        //Listar todos los restaurants con rating mayor a 4
        for (Document list : ratings) {
            System.out.println(list.toJson());
        }

        System.out.println("");

        //Checar restaurantes que incluyan la categoría pizza
        FindIterable<Document> categorias = collection.find(
                Filters.in("categoría", "pizza")
        );

        //Listar restaurantes que incluyan la categoría pizza
        for (Document list : categorias) {
            System.out.println(list.toJson());
        }

        System.out.println("");

        //Encontrar restaurantes que tengan sushi de nombre
        FindIterable<Document> nombres = collection.find(
                Filters.regex("name", Pattern.compile("^Sushi"))
        );

        //Listar restaurantes que tengan sushi de nombre
        for (Document list : nombres) {
            System.out.println(list.toJson());
        }

        System.out.println("");

        //Agregar categoría a sushilito
        UpdateResult update;
        update = collection.updateOne(
                Filters.eq("name", "Sushilito"),
                Updates.addToSet("categoría", "drink"));

        //Listar todos los restaurants
        for (Document list : collection.find()) {
            System.out.println(list.toJson());
        }
        
        System.out.println("");

        //Eliminar restaurants con 3 o menos estrellas
        DeleteResult deleteRating;
        deleteRating = collection.deleteMany(Filters.lte("rating", 3));

        for (Document list : collection.find()) {
            System.out.println(list.toJson());
        }

        //Borrar todos los registros
        DeleteResult delete;
        delete = collection.deleteMany(Filters.eq("name", "Sushilito"));
        delete = collection.deleteMany(Filters.eq("name", "Applebee's"));
        delete = collection.deleteMany(Filters.eq("name", "Chiltepino's"));
        delete = collection.deleteMany(Filters.eq("name", "Domino's Pizza"));
        delete = collection.deleteMany(Filters.eq("name", "Los Arbolitos"));
        delete = collection.deleteMany(Filters.eq("name", "Sushi Saga"));
        delete = collection.deleteMany(Filters.eq("name", "Mochomos"));
        delete = collection.deleteMany(Filters.eq("name", "Italianni's"));

    }

}
