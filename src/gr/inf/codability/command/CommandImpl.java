package gr.inf.codability.command;

import gr.inf.codability.core.Notifications;
import gr.inf.codability.functions.CaretPosition;

import java.util.HashMap;

import static gr.inf.codability.command.CommandDecoder.currentCmdType;
import static gr.inf.codability.functions.Create.createClass;
import static gr.inf.codability.functions.Create.createProject;
import static gr.inf.codability.functions.Execute.executeApp;
import static gr.inf.codability.functions.Insert.insertInstanceVar;
import static gr.inf.codability.functions.Insert.insertMain;
import static gr.inf.codability.functions.Open.openClass;
import static gr.inf.codability.functions.Open.openProject;
import static gr.inf.codability.functions.StringManipulation.getClassNameFormat;
import static gr.inf.codability.functions.StringManipulation.getVariableNameFormat;
import static gr.inf.codability.functions.StringToPsiType.getPsiType;
import static gr.inf.codability.functions.WordToNumber.convertWordToNum;

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

        String className = getClassNameFormat( nameOfClass, "name" );

        openClass( className );

    }

    static void createClassName( HashMap<String, String> nameOfClass )
    {
        if ( nameOfClass.get( "name" ) == null )
            return;

        String className = getClassNameFormat( nameOfClass, "name" );

        createClass( className );
    }

    static void createProjectByName( HashMap<String, String> nameOfProject )
    {
        if ( nameOfProject.get( "name" ) == null )
            return;

        String projectName = getClassNameFormat( nameOfProject, "name" );

        createProject( projectName );
    }

    static void openProjectByName( HashMap<String, String> nameOfProject )
    {
        if ( nameOfProject.get( "name" ) == null )
            return;

        String projectName = getClassNameFormat( nameOfProject, "name" );

        openProject( projectName );
    }

    static void runApp()
    {
        executeApp();
    }

    static void addInstanceVar( HashMap<String, String> params )
    {
        String className = getClassNameFormat( params, "class" );
        String varName = getVariableNameFormat( params, "name" );
        String type = params.get( "type" );

        insertInstanceVar(  varName, getPsiType( type ), className );
    }

    static void createMain()
    {
        insertMain();
    }
}
