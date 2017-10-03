package gr.inf.codability.functions;

import java.util.HashMap;

public class StringManipulation
{
    public static String getClassNameFormat( HashMap<String, String> word, String delimiter )
    {
        String[] tokens = word.get( delimiter ).split( "\\s" );
        StringBuilder newWord = new StringBuilder();

        for ( String str : tokens )
            newWord.append( str.substring( 0, 1 ).toUpperCase() + str.substring(1) );

        return newWord.toString().trim();
    }

    public static String getVariableNameFormat( HashMap<String, String> word, String delimiter )
    {
        String[] tokens = word.get( delimiter ).split( "\\s" );
        StringBuilder newWord = new StringBuilder();

        boolean firstWord = true;

        for ( String str : tokens )
        {
            if ( firstWord )
            {
                newWord.append( str );
                firstWord = false;
            }
            else
                newWord.append( str.substring( 0, 1 ).toUpperCase() + str.substring( 1 ) );
        }

        return newWord.toString().trim();
    }
}
