package gr.inf.codability.functions;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

public class Line
{
    // deleteLine( editor, project, lineNumber-1 );
    static void deleteLine( Editor editor, Project project, int lineNumber )
    {
        int start = editor.getDocument().getLineStartOffset( lineNumber );
        int end = editor.getDocument().getLineEndOffset( lineNumber ) + 1;

        new WriteCommandAction.Simple( project )
        {
            @Override
            protected void run() throws Throwable {
                editor.getDocument().deleteString( start, end );
            }
        }.execute();

    }

    static void insertNewLinesAtEnd( Editor editor, Project project, int newLines )
    {
        StringBuilder newLine = new StringBuilder();

        for ( int i = 0; i < newLines; i++ )
            newLine.append( '\n' );

        new WriteCommandAction.Simple( project )
        {
            @Override
            protected void run() throws Throwable {
                editor.getDocument().insertString( editor.getDocument().getTextLength(), newLine.toString() );
            }
        }.execute();
    }
}
