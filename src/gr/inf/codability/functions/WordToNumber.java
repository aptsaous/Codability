package gr.inf.codability.functions;

public class WordToNumber
{
    public static int convertWordToNum( String str )
    {
        int finalResult = 0;
        int partialResult = 0;

        String[] words = str.split( "\\s" );

        for ( String word : words )
        {
            switch ( word )
            {
                case "zero":
                case "oh":
                    partialResult += 0;
                    break;
                case "one":
                    partialResult += 1;
                    break;
                case "two":
                    partialResult += 2;
                    break;
                case "three":
                    partialResult += 3;
                    break;
                case "four":
                    partialResult += 4;
                    break;
                case "five":
                    partialResult += 5;
                    break;
                case "six":
                    partialResult += 6;
                    break;
                case "seven":
                    partialResult += 7;
                    break;
                case "eight":
                    partialResult += 8;
                    break;
                case "nine":
                    partialResult += 9;
                    break;
                case "ten":
                    partialResult += 10;
                    break;
                case "eleven":
                    partialResult += 11;
                    break;
                case "twelve":
                    partialResult += 12;
                    break;
                case "thirteen":
                    partialResult += 13;
                    break;
                case "fourteen":
                    partialResult += 14;
                    break;
                case "fifteen":
                    partialResult += 15;
                    break;
                case "sixteen":
                    partialResult += 16;
                    break;
                case "seventeen":
                    partialResult += 17;
                    break;
                case "eighteen":
                    partialResult += 18;
                    break;
                case "nineteen":
                    partialResult += 19;
                    break;
                case "twenty":
                    partialResult += 20;
                    break;
                case "thirty":
                    partialResult += 30;
                    break;
                case "forty":
                    partialResult += 40;
                    break;
                case "fifty":
                    partialResult += 50;
                    break;
                case "sixty":
                    partialResult += 60;
                    break;
                case "seventy":
                    partialResult += 70;
                    break;
                case "eighty":
                    partialResult += 80;
                    break;
                case "ninety":
                    partialResult += 90;
                    break;
                case "hundred":
                    partialResult *= 100;
                    break;
                case "thousand":
                    partialResult *= 1000;
                    finalResult += partialResult;
                    partialResult = 0;
                    break;

                default:
                    return -1;
            }

            finalResult += partialResult;
        }

        return finalResult;


    }
}
