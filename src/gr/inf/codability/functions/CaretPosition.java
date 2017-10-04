package gr.inf.codability.functions;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import gr.inf.codability.core.Notifications;

import static gr.inf.codability.functions.Line.insertNewLinesAtEnd;

public class CaretPosition
{
    public static void moveCaretToColumn( int columnNumber )
    {
        ApplicationManager.getApplication().invokeLater( () -> {

            DataContext dataContext = DataManager.getInstance().getDataContextFromFocus().getResult();
            Project project = DataKeys.PROJECT.getData( dataContext );

            if ( project == null )
            {
                Notifications.showWarning( "Caret position", "No opened projects have been found" );
                return;
            }

            Editor editor = FileEditorManager.getInstance( project ).getSelectedTextEditor();

            if ( editor == null )
            {
                Notifications.showWarning( "Caret position", "No active editor has been found" );
                return;
            }

            editor.getCaretModel().getCurrentCaret().moveToLogicalPosition( new LogicalPosition( editor.getCaretModel().getLogicalPosition().line, columnNumber - 1 ) );

            Notifications.showInfo( "Caret position", "Moved caret to column: " + columnNumber );

        });

    }

    public static void moveCaretToLine( int lineNumber )
    {
        ApplicationManager.getApplication().invokeLater( () -> {

            DataContext dataContext = DataManager.getInstance().getDataContextFromFocus().getResult();
            Project project = DataKeys.PROJECT.getData( dataContext );


            if ( project == null )
            {
                Notifications.showWarning( "Caret position", "No opened projects have been found" );
                return;
            }

            Editor editor = FileEditorManager.getInstance( project ).getSelectedTextEditor();

            if ( editor == null )
            {
                Notifications.showWarning( "Caret position", "No active editor has been found" );
                return;
            }

            if ( editor.getDocument().getLineCount() < lineNumber )
                insertNewLinesAtEnd( editor, project,lineNumber - editor.getDocument().getLineCount() );

            editor.getCaretModel().getCurrentCaret().moveToLogicalPosition( new LogicalPosition( lineNumber - 1, 0 ) );

            Notifications.showInfo( "Caret position", "Moved caret to line: " + lineNumber );

        });

    }

}
