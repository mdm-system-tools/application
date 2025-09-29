package org.MdmSystemTools.Application.utils

/**
 * Classe utilitária para gerenciar caminhos de assets de imagens.
 * Centraliza os caminhos para facilitar manutenção e evitar erros de digitação.
 */
object ImageAssets {

    // Backgrounds
    const val LOGIN_BACKGROUND = "images/backgrounds/login_bg.png"
    const val MAIN_BACKGROUND = "images/backgrounds/main_bg.png"
    const val SPLASH_BACKGROUND = "images/backgrounds/splash_bg.png"

    // Illustrations
    const val EMPTY_STATE_MEETINGS = "images/illustrations/empty_meetings.png"
    const val EMPTY_STATE_PROFILES = "images/illustrations/empty_profiles.png"
    const val WELCOME_ILLUSTRATION = "images/illustrations/welcome.png"

    // Photos/Avatars
    const val DEFAULT_AVATAR = "images/photos/default_avatar.png"
    const val PLACEHOLDER_PROFILE = "images/photos/placeholder_profile.png"

    // Animations
    const val LOADING_ANIMATION = "animations/loading.json"
    const val SUCCESS_ANIMATION = "animations/success.json"
    const val ERROR_ANIMATION = "animations/error.json"
}

/**
 * Extensão para facilitar o carregamento de assets
 */
fun String.asAssetPath(): String = this