package com.milsabores.backend;

import com.milsabores.backend.model.Product;
import com.milsabores.backend.model.User;
import com.milsabores.backend.repository.ProductRepository;
import com.milsabores.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // Inyectamos ambos repositorios
    public DataLoader(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🗑️ Limpiando base de datos...");
        productRepository.deleteAll();
        userRepository.deleteAll();

        loadUsers();    // <-- Cargamos usuarios
        loadProducts(); // <-- Cargamos productos
        
        System.out.println("✅ ¡Datos de prueba cargados exitosamente!");
    }

    private void loadUsers() {
        // Usuario ADMIN
        User admin = new User();
        admin.setName("Administrador");
        admin.setEmail("admin@milsabores.com");
        admin.setPassword("admin123"); // En la vida real esto se encripta
        admin.setRole("ADMIN");
        userRepository.save(admin);

        // Usuario CLIENTE
        User client = new User();
        client.setName("Cliente Juan");
        client.setEmail("juan@correo.com");
        client.setPassword("123456");
        client.setRole("CUSTOMER");
        userRepository.save(client);
        
        System.out.println("👤 Usuarios creados: admin@milsabores.com / juan@correo.com");
    }

    private void loadProducts() {
        // --- Tortas Cuadradas ---
        Product p1 = new Product();
        p1.setName("Torta Cuadrada de Chocolate");
        p1.setPrice(45000.0);
        p1.setCategory("tortas-cuadradas");
        p1.setImageUrl("https://i.pinimg.com/736x/d1/e6/49/d1e649ae8884cf9e8eeba48c2599db89.jpg");
        p1.setDescription("Deliciosa torta de chocolate con capas de ganache y un toque de avellanas.");

        Product p2 = new Product();
        p2.setName("Torta Cuadrada de Frutas");
        p2.setPrice(50000.0);
        p2.setCategory("tortas-cuadradas");
        p2.setImageUrl("https://i.pinimg.com/1200x/5a/3e/44/5a3e446ea093cd1477b85790e521f58b.jpg");
        p2.setDescription("Una mezcla de frutas frescas y crema chantilly sobre un suave bizcocho de vainilla.");

        // --- Tortas Circulares ---
        Product p3 = new Product();
        p3.setName("Torta Circular de Vainilla");
        p3.setPrice(40000.0);
        p3.setCategory("tortas-circulares");
        p3.setImageUrl("https://i.pinimg.com/736x/3e/68/56/3e68563b22627593ca68d5c3f32ac9d1.jpg");
        p3.setDescription("Bizcocho de vainilla clásico relleno con crema pastelera y cubierto con glaseado dulce.");

        Product p4 = new Product();
        p4.setName("Torta Circular de Manjar");
        p4.setPrice(42000.0);
        p4.setCategory("tortas-circulares");
        p4.setImageUrl("https://i.pinimg.com/1200x/f8/21/54/f82154e4f2e38793ce7af18488d27490.jpg");
        p4.setDescription("Torta tradicional chilena con manjar y nueces, un deleite para los amantes de los sabores clásicos.");

        // --- Postres Individuales ---
        Product p5 = new Product();
        p5.setName("Mousse de Chocolate");
        p5.setPrice(5000.0);
        p5.setCategory("postres-individuales");
        p5.setImageUrl("https://i.pinimg.com/1200x/f4/ce/e5/f4cee5e7149731cc9dd440115a194fbf.jpg");
        p5.setDescription("Postre individual cremoso y suave, hecho con chocolate de alta calidad.");

        Product p6 = new Product();
        p6.setName("Tiramisú Clásico");
        p6.setPrice(5500.0);
        p6.setCategory("postres-individuales");
        p6.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjsJpU_95skNNdIG9JRp8_gBFqEGQ4PAd5aA&s");
        p6.setDescription("Postre italiano con capas de café, mascarpone y cacao.");

        // --- Sin Azúcar ---
        Product p7 = new Product();
        p7.setName("Torta Sin Azúcar de Naranja");
        p7.setPrice(48000.0);
        p7.setCategory("sin-azucar");
        p7.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRoUo8-HIVi28cZg_s7f6Jpo2rKpT5AmPO7zw&s");
        p7.setDescription("Torta ligera y deliciosa, endulzada naturalmente, ideal para opciones saludables.");

        Product p8 = new Product();
        p8.setName("Cheesecake Sin Azúcar");
        p8.setPrice(47000.0);
        p8.setCategory("sin-azucar");
        p8.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLIMKrseuLvIqLkHtfM7jVZfUQV1Zo7ssyaA&s");
        p8.setDescription("Suave y cremoso, perfecto para disfrutar sin culpa.");

        // --- Pastelería Tradicional ---
        Product p9 = new Product();
        p9.setName("Empanada de Manzana");
        p9.setPrice(3000.0);
        p9.setCategory("pasteleria-tradicional");
        p9.setImageUrl("https://i.pinimg.com/736x/b4/e4/7d/b4e47d6e777bfde9e072f2ffe096c1ef.jpg");
        p9.setDescription("Pastelería tradicional rellena de manzanas especiadas.");

        Product p10 = new Product();
        p10.setName("Tarta de Santiago");
        p10.setPrice(6000.0);
        p10.setCategory("pasteleria-tradicional");
        p10.setImageUrl("https://yhoyquecomemos.com/wp-content/uploads/2022/03/receta-de-tarta-de-santiago.jpg");
        p10.setDescription("Tradicional tarta española hecha con almendras, azúcar y huevos.");

        // --- Sin Gluten ---
        Product p11 = new Product();
        p11.setName("Brownie Sin Gluten");
        p11.setPrice(4000.0);
        p11.setCategory("sin-gluten");
        p11.setImageUrl("https://i.pinimg.com/736x/60/f5/a5/60f5a5ce8775a0fcc170194b69793597.jpg");
        p11.setDescription("Rico y denso, perfecto para quienes evitan el gluten.");

        Product p12 = new Product();
        p12.setName("Pan Sin Gluten");
        p12.setPrice(3500.0);
        p12.setCategory("sin-gluten");
        p12.setImageUrl("https://i.pinimg.com/736x/20/f0/1a/20f01a160ee9bd1060e95f02a222d40b.jpg");
        p12.setDescription("Suave y esponjoso, ideal para sándwiches.");

        // --- Vegana ---
        Product p13 = new Product();
        p13.setName("Torta Vegana de Chocolate");
        p13.setPrice(50000.0);
        p13.setCategory("vegana");
        p13.setImageUrl("https://i.pinimg.com/1200x/ca/1b/c2/ca1bc26d0dfe365207cf7a01b08995a4.jpg");
        p13.setDescription("Torta de chocolate húmeda y deliciosa, hecha sin productos de origen animal.");

        Product p14 = new Product();
        p14.setName("Galletas Veganas de Avena");
        p14.setPrice(4500.0);
        p14.setCategory("vegana");
        p14.setImageUrl("https://i.pinimg.com/1200x/f4/d7/e2/f4d7e20d8ecbe5b5da7a78c4ce56fe4d.jpg");
        p14.setDescription("Crujientes y sabrosas, una excelente opción saludable.");

        // --- Tortas Especiales ---
        Product p15 = new Product();
        p15.setName("Torta Especial de Cumpleaños");
        p15.setPrice(55000.0);
        p15.setCategory("tortas-especiales");
        p15.setImageUrl("https://i.pinimg.com/736x/ea/2f/c2/ea2fc20aa861070fd19d4d11330fa2bb.jpg");
        p15.setDescription("Diseñada para celebraciones, personalizable con decoraciones.");

        Product p16 = new Product();
        p16.setName("Torta Especial de Boda");
        p16.setPrice(60000.0);
        p16.setCategory("tortas-especiales");
        p16.setImageUrl("https://i.pinimg.com/736x/91/b8/8e/91b88e2b5ff9842104ed2262c453564a.jpg");
        p16.setDescription("Elegante y deliciosa, el centro de atención en cualquier boda.");

        // GUARDAR TODOS
        productRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16));
    }
}