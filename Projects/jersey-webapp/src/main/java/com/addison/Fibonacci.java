package com.addison;

import java.math.BigInteger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "fibonacci" path)
 */
@Path("fibonacci")
public class Fibonacci {

    /**
     * Method handling HTTP GET requests. The returned object will be sent to the client as "text/plain" media type.
     * 
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String fibonacci(@QueryParam("num") String number) {
        try {
            long n = Long.parseLong(number);
            StringBuilder series = new StringBuilder("Fibonacci ");
            series.append(number);
            series.append(" : 0");

            if (n > 1)
                series.append(" 1");

            BigInteger prev = BigInteger.valueOf(0L), next = BigInteger.valueOf(1L), result = BigInteger.valueOf(0L);
            for (long i = 1; i < n - 1; i++) {
                result = prev.add(next);
                series.append(" ");
                series.append(result.toString());
                prev = next;
                next = result;
            }

            return series.toString();
        } catch (NumberFormatException err) {
            return "Invald Request:  fibonacci?num=" + number + "\nNumberFormatException: " + err.getMessage();
        }
    }
}
