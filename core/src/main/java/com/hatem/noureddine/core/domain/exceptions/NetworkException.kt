package com.hatem.noureddine.core.domain.exceptions

/**
 * Exception occur when the is no network connexion
 */
class NetworkException :
    Throwable("Cannot reach network host, please verify your network connexion")