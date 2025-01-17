/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:OptIn(ExperimentalHorologistApi::class)

package com.android.credentialmanager.ui.screens.single.passkey

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.android.credentialmanager.FlowEngine
import com.android.credentialmanager.model.get.CredentialEntryInfo
import com.android.credentialmanager.R
import com.android.credentialmanager.ui.components.AccountRow
import com.android.credentialmanager.ui.components.ContinueChip
import com.android.credentialmanager.ui.components.CredentialsScreenChipSpacer
import com.android.credentialmanager.ui.components.DismissChip
import com.android.credentialmanager.ui.components.SignInHeader
import com.android.credentialmanager.ui.components.SignInOptionsChip
import com.android.credentialmanager.ui.screens.single.SingleAccountScreen
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumnState
import com.android.credentialmanager.ui.components.BottomSpacer

/**
 * Screen that shows single passkey credential.
 *
 * @param entry The passkey entry
 * @param columnState ScalingLazyColumn configuration to be be applied to SingleAccountScreen
 * @param modifier styling for composable
 * @param flowEngine [FlowEngine] that updates ui state for this screen
 */
@OptIn(ExperimentalHorologistApi::class)
@Composable
fun SinglePasskeyScreen(
    entry: CredentialEntryInfo,
    columnState: ScalingLazyColumnState,
    flowEngine: FlowEngine,
) {
    SingleAccountScreen(
        headerContent = {
            SignInHeader(
                icon = entry.icon,
                title = stringResource(R.string.use_passkey_title),
            )
        },
        accountContent = {
            val displayName = entry.displayName
            if (displayName == null ||
                entry.displayName.equals(entry.userName, ignoreCase = true)) {
                AccountRow(
                    primaryText = entry.userName,
                )
            } else {
                AccountRow(
                    primaryText = displayName,
                    secondaryText = entry.userName,
                )
            }
        },
        columnState = columnState,
    ) {
        item {
            val selectEntry = flowEngine.getEntrySelector()
            Column {
                ContinueChip { selectEntry(entry, false) }
                CredentialsScreenChipSpacer()
                SignInOptionsChip{ flowEngine.openSecondaryScreen() }
                CredentialsScreenChipSpacer()
                DismissChip { flowEngine.cancel() }
                BottomSpacer()
            }
        }
    }
}
