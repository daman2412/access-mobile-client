/*
 *  This file is part of the IOTA Access distribution
 *  (https://github.com/iotaledger/access)
 *
 *  Copyright (c) 2020 IOTA Stiftung.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.iota.access.ui.main.delegation.preview

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import org.iota.access.BaseFragment
import org.iota.access.R
import org.iota.access.di.Injectable
import org.iota.access.ui.main.delegation.DelegationSharedViewModel
import org.json.JSONObject
import javax.inject.Inject

class DelegationPreviewJsonFragment : BaseFragment(R.layout.fragment_delegation_preview_json), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val storeOwner = navController.getViewModelStoreOwner(navController.graph.id)
        val delegationSharedViewModel = ViewModelProvider(storeOwner, viewModelFactory).get(DelegationSharedViewModel::class.java)

        val policy = delegationSharedViewModel.previewingPolicy ?: return

        val text = JSONObject(policy.toMap()).toString(2)
        view?.findViewById<TextView>(R.id.text_view)?.text = text
    }

    companion object {
        fun newInstance(): DelegationPreviewJsonFragment = DelegationPreviewJsonFragment()
    }
}
