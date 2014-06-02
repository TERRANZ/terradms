
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
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CommonDTO_QNAME = new QName("", "commonDTO");
    private final static QName _LoginDTO_QNAME = new QName("", "loginDTO");
    private final static QName _ObjectDTO_QNAME = new QName("", "objectDTO");
    private final static QName _ListDTO_QNAME = new QName("", "listDTO");
    private final static QName _Pair_QNAME = new QName("", "pair");
    private final static QName _SimpleDataDTO_QNAME = new QName("", "simpleDataDTO");
    private final static QName _UserDTO_QNAME = new QName("", "userDTO");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.terra.dms.client.rest
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CommonDTO }
     */
    public CommonDTO createCommonDTO() {
        return new CommonDTO();
    }

    /**
     * Create an instance of {@link LoginDTO }
     */
    public LoginDTO createLoginDTO() {
        return new LoginDTO();
    }

    /**
     * Create an instance of {@link ObjectDTO }
     */
    public ObjectDTO createObjectDTO() {
        return new ObjectDTO();
    }

    /**
     * Create an instance of {@link ListDTO }
     */
    public ListDTO createListDTO() {
        return new ListDTO();
    }

    /**
     * Create an instance of {@link Pair }
     */
    public Pair createPair() {
        return new Pair();
    }

    /**
     * Create an instance of {@link SimpleDataDTO }
     */
    public SimpleDataDTO createSimpleDataDTO() {
        return new SimpleDataDTO();
    }

    /**
     * Create an instance of {@link UserDTO }
     */
    public UserDTO createUserDTO() {
        return new UserDTO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommonDTO }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "commonDTO")
    public JAXBElement<CommonDTO> createCommonDTO(CommonDTO value) {
        return new JAXBElement<CommonDTO>(_CommonDTO_QNAME, CommonDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginDTO }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "loginDTO")
    public JAXBElement<LoginDTO> createLoginDTO(LoginDTO value) {
        return new JAXBElement<LoginDTO>(_LoginDTO_QNAME, LoginDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectDTO }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "objectDTO")
    public JAXBElement<ObjectDTO> createObjectDTO(ObjectDTO value) {
        return new JAXBElement<ObjectDTO>(_ObjectDTO_QNAME, ObjectDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListDTO }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "listDTO")
    public JAXBElement<ListDTO> createListDTO(ListDTO value) {
        return new JAXBElement<ListDTO>(_ListDTO_QNAME, ListDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Pair }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "pair")
    public JAXBElement<Pair> createPair(Pair value) {
        return new JAXBElement<Pair>(_Pair_QNAME, Pair.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SimpleDataDTO }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "simpleDataDTO")
    public JAXBElement<SimpleDataDTO> createSimpleDataDTO(SimpleDataDTO value) {
        return new JAXBElement<SimpleDataDTO>(_SimpleDataDTO_QNAME, SimpleDataDTO.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserDTO }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "userDTO")
    public JAXBElement<UserDTO> createUserDTO(UserDTO value) {
        return new JAXBElement<UserDTO>(_UserDTO_QNAME, UserDTO.class, null, value);
    }

}
