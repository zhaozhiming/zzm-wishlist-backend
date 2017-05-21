
package com.sample.wishlistDemo.api.generated;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.wishlistDemo.utils.RestClient;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;

/**
* Resource class containing the custom logic. Please put your logic here!
*/
@Component("apiWishlistsResource")
@Singleton
public class DefaultWishlistsResource implements com.sample.wishlistDemo.api.generated.WishlistsResource
{
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultWishlistsResource.class);
    private ObjectMapper mapper = new ObjectMapper();

    private RestClient rest = new RestClient();
    @javax.ws.rs.core.Context
	private javax.ws.rs.core.UriInfo uriInfo;

    /* GET / */
	@Override
	public Response get(final YaasAwareParameters yaasAware) {
        return Response.ok().entity(new Wishlist()).build();
    }

	/* POST / */
	@Override
	public Response post(final YaasAwareParameters yaasAware, final Wishlist wishlist)
	{
        try {
            String token = rest.manageToken();
            LOG.debug(token);
            Map<String, String> map = mapper.readValue(token, new TypeReference<Map<String, String>>() {
            });
            LOG.debug(map.toString());
            String accessToken = map.get("access_token");
            String result = rest.createWishlist(mapper.writeValueAsString(wishlist), accessToken);
            LOG.debug(result);
            return Response.created(uriInfo.getAbsolutePath()).entity(result).build();
        } catch (IOException e) {
            return Response.serverError().entity(e).build();
        }
    }

	/* GET /{wishlistId} */
	@Override
	public Response getByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId)
	{
        try {
            String token = rest.viewToken();
            LOG.debug(token);
            Map<String, String> map = mapper.readValue(token, new TypeReference<Map<String, String>>() {
            });
            LOG.debug(map.toString());
            String accessToken = map.get("access_token");
            String result = rest.getByWishlistId(accessToken, wishlistId);
            LOG.debug(result);
            return Response.ok().entity(result).build();
        } catch (IOException e) {
            return Response.serverError().entity(e).build();
        }
	}

	/* PUT /{wishlistId} */
	@Override
	public Response putByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId, final Wishlist wishlist)
	{
        return Response.noContent().build();
	}

	/* DELETE /{wishlistId} */
	@Override
	public Response deleteByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId)
	{
		// place some logic here
		return Response.noContent().build();
	}

	@Override
	public
	Response getByWishlistIdWishlistItems(
			final YaasAwareParameters yaasAware,  final java.lang.String wishlistId)
	{
		// place some logic here
		return Response.ok()
                .entity(new java.util.ArrayList<WishlistItem>()).build();
	}

	@Override
	public
	Response postByWishlistIdWishlistItems(final YaasAwareParameters yaasAware,
			final java.lang.String wishlistId, final WishlistItem wishlistItem){
        try {
            String token = rest.manageToken();
            LOG.debug(token);
            Map<String, String> map = mapper.readValue(token, new TypeReference<Map<String, String>>() {
            });
            LOG.debug(map.toString());
            String accessToken = map.get("access_token");
            String result = rest.addWishlistItem(wishlistId, mapper.writeValueAsString(wishlistItem), accessToken);
            LOG.debug(result);
            return Response.created(uriInfo.getAbsolutePath()).entity(result).build();
        } catch (IOException e) {
            return Response.serverError().entity(e).build();
        }
	}
}
