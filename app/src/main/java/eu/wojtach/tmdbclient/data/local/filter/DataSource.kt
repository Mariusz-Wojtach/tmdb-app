package eu.wojtach.tmdbclient.data.local.filter

import org.koin.core.annotation.Single

@Single
class DataSource {

    private var selectedFilterId: Long? = null

    fun setSelectedFilterId(id: Long) {
        selectedFilterId = id
    }

    fun getSelectedFilterId(): Long? {
        return selectedFilterId
    }

    fun clearSelectedFilterId() {
        selectedFilterId = null
    }
}
