package gr.inf.codability.core;

import java.util.Random;

public class ActivationCodeGenerator
{
    public static String activationCode;

    static String getActivationCode()
    {
        Random random = new Random();
        activationCode = String.format( "%04d", random.nextInt( 10000 ) );

        if ( activationCode.startsWith( "0" ) )
            activationCode = activationCode.replaceFirst( "0", "1" );

        return activationCode;
    }
}
