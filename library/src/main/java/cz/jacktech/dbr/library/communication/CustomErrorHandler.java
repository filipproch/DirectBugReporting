package cz.jacktech.dbr.library.communication;

import java.net.HttpURLConnection;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by toor on 21.7.14.
 */
public class CustomErrorHandler implements ErrorHandler{
    @Override
    public Throwable handleError(RetrofitError cause) {
        Response r = cause.getResponse();
        if(r.getStatus() == HttpURLConnection.HTTP_UNAUTHORIZED){
            return new UnauthorizedException(cause);
        }
        if(r.getStatus() == HttpURLConnection.HTTP_NOT_FOUND){
            return new UnauthorizedException(cause);
        }
        if(r.getStatus() == HttpURLConnection.HTTP_FORBIDDEN){
            return new UnauthorizedException(cause);
        }
        return cause;
    }

    public static class UnauthorizedException extends Exception{
        private RetrofitError retrofitError;
        public UnauthorizedException(RetrofitError retrofitError) {
            this.retrofitError = retrofitError;
        }

        public RetrofitError getRetrofitError() {
            return retrofitError;
        }
    }
}
