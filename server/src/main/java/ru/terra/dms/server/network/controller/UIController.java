package ru.terra.dms.server.network.controller;

import com.sun.jersey.api.core.HttpContext;
import ru.terra.dms.server.constants.URLConstants;
import ru.terra.server.controller.AbstractResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Date: 22.07.14
 * Time: 9:25
 */
@Path(URLConstants.UI.UI)
public class UIController extends AbstractResource {
    @Path(URLConstants.UI.MAIN)
    @GET
    @Produces({"text/html"})
    public Response getMain(@Context HttpContext hc) {
        if (isAuthorized(hc))
            return returnHtmlFile("html/main.html");
        else
            return getLogin(hc);
    }

    @Path(URLConstants.UI.Login.URL)
    @GET
    @Produces({"text/html"})
    public Response getLogin(@Context HttpContext hc) {
        if (isAuthorized(hc))
            return getMain(hc);
        else
            return returnHtmlFile("html/login.html");
    }

    @Path(URLConstants.UI.Configuration.URL)
    @GET
    @Produces({"text/html"})
    public Response getConfiguration(@Context HttpContext hc) {
        if (isAuthorized(hc))
            return getMain(hc);
        else
            return returnHtmlFile("html/configuration.html");
    }

    @Path(URLConstants.UI.Configuration.Menu.URL)
    @GET
    @Produces({"text/html"})
    public Response getMenu(@Context HttpContext hc) {
        if (isAuthorized(hc))
            return getMain(hc);
        else
            return returnHtmlFile("html/menus.html");
    }

    @Path(URLConstants.UI.Configuration.ViewPart.URL)
    @GET
    @Produces({"text/html"})
    public Response getViewpart(@Context HttpContext hc) {
        if (isAuthorized(hc))
            return getMain(hc);
        else
            return returnHtmlFile("html/viewpart.html");
    }

    @Path(URLConstants.UI.Configuration.ViewPart.Pojo.URL)
    @GET
    @Produces({"text/html"})
    public Response getPojo(@Context HttpContext hc) {
        if (isAuthorized(hc))
            return getMain(hc);
        else
            return returnHtmlFile("html/pojo.html");
    }
}
