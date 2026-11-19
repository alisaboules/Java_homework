package Homework3;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

public class XmlAddressParser implements AddressParser {

  private static final String ITEM_TAG = "item";

  private final XMLInputFactory factory = createFactory();

  private static XMLInputFactory createFactory() {
    XMLInputFactory f = XMLInputFactory.newFactory();
    f.setProperty(XMLInputFactory.SUPPORT_DTD, false);
    f.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
    return f;
  }

  @Override
  public void parse(Path file, Consumer<Address> consumer) throws IOException {
    try (InputStream in = Files.newInputStream(file)) {
      XMLStreamReader reader = factory.createXMLStreamReader(in);
      try {
        while (reader.hasNext()) {
          if (reader.next() == XMLStreamConstants.START_ELEMENT && ITEM_TAG.equals(reader.getLocalName())) {
            consumer.accept(readItem(reader));
          }
        }
      } finally {
        reader.close();
      }
    } catch (XMLStreamException e) {
      throw new IOException("XML: ошибка разбора файла: " + e.getMessage(), e);
    }
  }

  private Address readItem(XMLStreamReader reader) throws IOException {
    try {
      return new Address(
          requireAttribute(reader, "city"),
          requireAttribute(reader, "street"),
          Integer.parseInt(requireAttribute(reader, "house").trim()),
          Integer.parseInt(requireAttribute(reader, "floor").trim()));
    } catch (NumberFormatException e) {
      throw new IOException(
          "XML: некорректный номер дома или этаж (строка " + reader.getLocation().getLineNumber() + ")", e);
    }
  }

  private String requireAttribute(XMLStreamReader reader, String name) throws IOException {
    String value = reader.getAttributeValue(null, name);
    if (value == null) {
      throw new IOException(
          "XML: у элемента <item> нет атрибута '" + name + "' (строка " + reader.getLocation().getLineNumber() + ")");
    }
    return value;
  }
}