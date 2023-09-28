import Server.GServer;
import image.DefaultTextColorSchema;
import image.TextColorSchema;
import image.TextConverter;
import image.TextGraphicsConverter;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new TextConverter(); // Создайте тут объект вашего класса конвертера
        GServer server = new GServer(converter);
        TextColorSchema schema = new DefaultTextColorSchema();
        converter.setTextColorSchema(schema);// Создаём объект сервера
        server.start(); // Запускаем

    }
}
