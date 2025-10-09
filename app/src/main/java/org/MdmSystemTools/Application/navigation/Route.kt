package org.MdmSystemTools.Application.navigation

sealed interface Route {
	val destination: String

	data object Associate : Route {
		override val destination = "/associate"
	}

	data object Collaboration : Route {
		override val destination = "/collaboration"
	}

	data object Calendar : Route {
		override val destination = "/calendar"
	}

	data object Form : Route {
		override val destination = "/form"
	}

	data object Login : Route {
		override val destination = "/login"
	}

	data object Register : Route {
		override val destination = "/register"
	}

	data object AddEvent : Route {
		override val destination = "/addevent"
	}
}