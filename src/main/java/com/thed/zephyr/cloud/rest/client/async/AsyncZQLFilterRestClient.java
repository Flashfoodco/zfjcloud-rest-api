package com.thed.zephyr.cloud.rest.client.async;

import com.atlassian.httpclient.api.ResponsePromise;
import com.thed.zephyr.cloud.rest.model.ZQLFilter;

/**
 * Created by Kavya on 28-06-2016.
 */
public interface AsyncZQLFilterRestClient {

    ResponsePromise getFilter(String filterId);

    ResponsePromise getFilters(boolean byUser, boolean fav, int offset, int maxRecords);

    ResponsePromise quickSearch(String query);

    ResponsePromise search(String name, String owner, String sharePerm);

    ResponsePromise deleteFilter(String filterId);

    ResponsePromise saveFilter(ZQLFilter zqlFilter);

    ResponsePromise updateFilter(String filterId, ZQLFilter zqlFilter);

    ResponsePromise copyFilter(ZQLFilter zqlFilter);

    ResponsePromise toggleFavorite(String filterId);
}
