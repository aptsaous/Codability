package gr.inf.codability.command;

import gr.inf.codability.core.Notifications;
import gr.inf.codability.functions.CaretPosition;

import java.util.HashMap;

import static gr.inf.codability.functions.WordToNumber.convertWordToNum;
import static gr.inf.codability.command.CommandDecoder.currentCmdType;

class CommandImpl
{
    static void go_to( HashMap<String, String> params )
    {
        if ( params.containsKey( "line" ) )
        {
            if ( params.get( "line" ) != null )
            {
                int lineNum;

                try
                {
                    lineNum = Integer.parseInt( params.get( "line" ) );
                    CaretPosition.moveCaretToLine( lineNum );
                }
                catch ( NumberFormatException e )
                {
                    lineNum = convertWordToNum( params.get( "line" ) );

                    if ( lineNum != - 1 )
                    {
                        CaretPosition.moveCaretToLine( lineNum );
                    }
                    else
                    {
                        Notifications.showWarning( "Move caret to line", "Invalid parameter: " + params.get( "line" ) );
                        return;
                    }

                }
            }
            else
                Notifications.showWarning( "Move caret to line", "No line number specified" );

        }

        if ( params.containsKey( "column" ) )
        {
            if ( params.get( "column" ) != null )
            {
                int columnNum;

                try
                {
                    columnNum = Integer.parseInt( params.get( "column" ) );
                    CaretPosition.moveCaretToColumn( columnNum );
                }
                catch ( NumberFormatException e )
                {
                    columnNum = convertWordToNum( params.get( "column" ) );

                    if ( columnNum != - 1 )
                    {
                        CaretPosition.moveCaretToColumn( columnNum );
                    }
                    else
                    {
                        Notifications.showWarning( "Move caret to column", "Invalid parameter: " + params.get( "column" ) );
                    }

                }

            }
            else
                Notifications.showWarning( "Move caret to column", "No column number specified" );

        }
    }

    static void change_mode( CommandType type )
    {
        if ( type != currentCmdType )
            currentCmdType = type;

        switch ( type )
        {
            case HYBRID:
                Notifications.showInfo( "Mode Changed", "Hybrid mode" );
                break;
            case INS:
                Notifications.showInfo( "Mode Changed", "Insert mode" );
                break;
            case OP:
                Notifications.showInfo( "Mode Changed", "Op mode" );
                break;

        }
    }

}
