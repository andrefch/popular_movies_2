package br.com.andrefch.popularmoviesii.data.repository.remote;

/**
 * Author: andrech
 * Date: 16/02/18
 */

class APIException extends Exception {

    APIException(String message, Throwable cause) {
        super(message, cause);
    }
}
