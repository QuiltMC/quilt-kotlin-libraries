/*
 * Copyright 2023 The Quilt Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quiltmc.qkl.library

/**
 * A "containing" object to make event registration callbacks less polluting to the
 * global namespace.
 *
 * @author sschr15
 */
public object EventRegistration

/**
 * When registering events through QKL, this function gives a context allowing you to
 * not have to repeatedly type `EventRegistration.onEventName` when registering
 * many events.
 *
 * @author sschr15
 */
public fun registerEvents(action: EventRegistration.() -> Unit) {
    EventRegistration.apply(action)
}
