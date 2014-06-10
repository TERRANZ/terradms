
package ru.terra.dms.client.rest;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.terra.dms.client.rest package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Pojo_QNAME = new QName("", "pojo");
    private final static QName _MenuPart_QNAME = new QName("", "menuPart");
    private final static QName _CommonDTO_QNAME = new QName("", "commonDTO");
    private final static QName _Configuration_QNAME = new QName("", "configuration");
    private final static QName _LoginDTO_QNAME = new QName("", "loginDTO");
    private final static QName _ObjectDTO_QNAME = new QName("", "objectDTO");
    private final static QName _ListDTO_QNAME = new QName("", "listDTO");
    private final static QName _ViewPart_QNAME = new QName("", "viewPart");
    private final static QName _SimpleDataDTO_QNAME = new QName("", "simpleDataDTO");
    private final static QName _UserDTO_QNAME = new QName("", "userDTO");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.terra.dms.client.rest
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ObjectDTO }
     * 
     */
    public ObjectDTO createObjectDTO() {
        return new ObjectDTO();
    }

    /**
     * Create an instance of {@link ObjectDTO.Fields }
     * 
     */
    public ObjectDTO.Fields createObjectDTOFields() {
        return new ObjectDTO.Fields();
    }

    /**
     * Create an instance of {@link Pojo }
     * 
     */
    public Pojo createPojo() {
        return new Pojo();
    }

    /**
     * Create an instance of {@link Pojo.Fields }
     * 
     */
    public Pojo.Fields createPojoFields() {
        return new Pojo.Fields();
    }

    /**
     * Create an instance of {@link MenuPart }
     * 
     */
    public MenuPart createMenuPart() {
        return new MenuPart();
    }

    /**
     * Create an instance of {@link CommonDTO }
     * 
     */
    public CommonDTO createCommonDTO() {
        return new CommonDTO();
    }

    /**
     * Create an instance of {@link Configuration }
     * 
     */
    public Configuration createConfiguration() {
        return new Configuration();
    }

    /**
     * Create an instance of {@link LoginDTO }
     * 
     */
    public LoginDTO createLoginDTO() {
        return new LoginDTO();
    }

    /**
     * Create an instance of {@link ListDTO }
     * 
     */
    public ListDTO createListDTO() {
        return new ListDTO();
    }

    /**
     * Create an instance of {@link ViewPart }
     * 
     */
    public ViewPart createViewPart() {
        return new ViewPart();
    }

    /**
     * Create an instance of {@link SimpleDataDTO }
     * 
     */
    public SimpleDataDTO createSimpleDataDTO() {
        return new SimpleDataDTO();
    }

    /**
     * Create an instance of {@link UserDTO }
     * 
     */
    public UserDTO createUserDTO() {
        return new UserDTO();
    }

    /**
     * Create an instance of {@link ObjectDTO.Fields.Entry }
     * 
     */
    public ObjectDTO.Fields.Entry createObjectDTOFieldsEntry() {
        return new ObjectDTO.Fields.Entry();
    }

    /**
     * Create an instance of {@link Pojo.Fields.Entry }
     * 
     */
    public Pojo.Fields.Entry createPojoFieldsEntry() {
        return new Pojo.Fields.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Pojo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "pojo")
    public JAXBElement<Pojo> createPojo(Pojo value) {
        return new JAXBElement<Pojo>(_Pojo_QNAME, Pojo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MenuPart }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "menuPart")
    public JAXBElement<MenuPart> createMenuPart(MenuPart value) {
        return new JAXBElement<MenuPart>(_MenuPart_QNAME, MenuPart.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommonDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "commonDTO")
    public JAXBElement<CommonDTO> createCommonDTO(CommonDTO value) {
        return new JAXBElement<CommonDTO>(_CommonDTO_QNAME, CommonDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Configuration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "configuration")
    public JAXBElement<Configuration> createConfiguration(Configuration value) {
        return new JAXBElement<Configuration>(_Configuration_QNAME, Configuration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "loginDTO")
    public JAXBElement<LoginDTO> createLoginDTO(LoginDTO value) {
        return new JAXBElement<LoginDTO>(_LoginDTO_QNAME, LoginDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "objectDTO")
    public JAXBElement<ObjectDTO> createObjectDTO(ObjectDTO value) {
        return new JAXBElement<ObjectDTO>(_ObjectDTO_QNAME, ObjectDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "listDTO")
    public JAXBElement<ListDTO> createListDTO(ListDTO value) {
        return new JAXBElement<ListDTO>(_ListDTO_QNAME, ListDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewPart }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "viewPart")
    public JAXBElement<ViewPart> createViewPart(ViewPart value) {
        return new JAXBElement<ViewPart>(_ViewPart_QNAME, ViewPart.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SimpleDataDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "simpleDataDTO")
    public JAXBElement<SimpleDataDTO> createSimpleDataDTO(SimpleDataDTO value) {
        return new JAXBElement<SimpleDataDTO>(_SimpleDataDTO_QNAME, SimpleDataDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserDTO }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "userDTO")
    public JAXBElement<UserDTO> createUserDTO(UserDTO value) {
        return new JAXBElement<UserDTO>(_UserDTO_QNAME, UserDTO.class, null, value);
    }

}
