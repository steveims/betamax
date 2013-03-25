/*
 * Copyright 2011 Rob Fletcher
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

package co.freeside.betamax

import co.freeside.betamax.message.Request

/**
 * Implements a request matching rule for finding recordings on a tape.
 */
enum MatchRule implements Comparator<Request> {
	method {
		@Override
		int compare(Request a, Request b) {
			a.method <=> b.method
		}
	},
	uri {
		@Override
		int compare(Request a, Request b) {
			a.uri <=> b.uri
		}
	},
	host {
		@Override
		int compare(Request a, Request b) {
			a.uri.host <=> b.uri.host
		}
	},
	path {
		@Override
		int compare(Request a, Request b) {
			a.uri.path <=> b.uri.path
		}
	},
	port {
		@Override
		int compare(Request a, Request b) {
			a.uri.port <=> b.uri.port
		}
	},
	query {
		@Override
		int compare(Request a, Request b) {
			a.uri.query <=> b.uri.query
		}
	},
	fragment {
		@Override
		int compare(Request a, Request b) {
			a.uri.fragment <=> b.uri.fragment
		}
	},
	header_accept {
		@Override
		int compare(Request a, Request b) {
		        String name = 'accept'
			getHeaderValue(name, a) <=> getHeaderValue(name, b)
		}
	},
	header_authorization {
		@Override
		int compare(Request a, Request b) {
		        String name = 'authorization'
			getHeaderValue(name, a) <=> getHeaderValue(name, b)
		}
	},
	header_contentlength {
		@Override
		int compare(Request a, Request b) {
		        String name = 'content-length'
			getHeaderValue(name, a) <=> getHeaderValue(name, b)
		}
	},
	header_contenttype {
		@Override
		int compare(Request a, Request b) {
		        String name = 'content-type'
			getHeaderValue(name, a) <=> getHeaderValue(name, b)
		}
	},
	headers {
		@Override
		int compare(Request a, Request b) {
			def result = a.headers.size() <=> b.headers.size()
			if (result != 0) {
				return result
			}
			if (a.headers.keySet() != b.headers.keySet()) {
				return -1 // wouldn't work if we cared about ordering...
			}
			for (header in a.headers) {
				result = header.value <=> b.headers[header.key]
				if (result != 0) {
					return result
				}
			}
			0
		}
	},
	body {
		@Override
		int compare(Request a, Request b) {
			a.bodyAsText.text <=> b.bodyAsText.text
		}
	}

	int compare(Request a, Request b) {
		throw new UnsupportedOperationException()
	}

	// Header names are case-insensitive
	String getHeaderValue(String name, Request r) {
	        String key = r.headers.keySet().find {
		        it.toLowerCase() == name
		}

		key ? r.headers[key] : null
	}
}
