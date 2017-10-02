package gr.inf.codability.command;

import gr.inf.codability.core.Notifications;
import gr.inf.codability.functions.CaretPosition;

import java.util.HashMap;
import java.util.LinkedList;

import static gr.inf.codability.command.CommandType.INS;
import static gr.inf.codability.command.CommandType.OP;
import static gr.inf.codability.functions.Create.createProject;
import static gr.inf.codability.functions.Open.openClass;
import static gr.inf.codability.functions.Create.createClass;
import static gr.inf.codability.functions.WordToNumber.convertWordToNum;
import static gr.inf.codability.command.CommandDecoder.currentCmdType;

class CommandImpl
{
    static void goTo( HashMap<String, String> params )
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

    static void changeMode( CommandType type )
    {
        if ( currentCmdType != type )
            currentCmdType = type;

        switch ( type )
        {
            case INS:
                Notifications.showInfo( "Mode Changed", "Insert mode" );
                break;
            case OP:
                Notifications.showInfo( "Mode Changed", "Op mode" );
                break;
        }
    }

    static void openClassName( HashMap<String, String> nameOfClass )
    {
        if ( nameOfClass.get( "name" ) == null )
            return;

        String[] tokens = nameOfClass.get( "name" ).split( "\\s" );
        StringBuilder className = new StringBuilder();

        for ( String str : tokens )
        {
            className.append( str.substring( 0, 1 ).toUpperCase() + str.substring(1) );
        }

        openClass( className.toString().trim() );

    }

    static void createClassName( HashMap<String, String> nameOfClass )
    {
        if ( nameOfClass.get( "name" ) == null )
            return;

        String[] tokens = nameOfClass.get( "name" ).split( "\\s" );
        StringBuilder className = new StringBuilder();

        for ( String str : tokens )
        {
            className.append( str.substring( 0, 1 ).toUpperCase() + str.substring(1) );
        }

        createClass( className.toString().trim() );
    }

    static void createProjectByName( HashMap<String, String> nameOfProject )
    {
        if ( nameOfProject.get( "name" ) == null )
            return;

        String[] tokens = nameOfProject.get( "name" ).split( "\\s" );
        StringBuilder projectName = new StringBuilder();

        for ( String str : tokens )
        {
            projectName.append( str.substring( 0, 1 ).toUpperCase() + str.substring(1) );
        }

        createProject( projectName.toString().trim() );
    }

}
