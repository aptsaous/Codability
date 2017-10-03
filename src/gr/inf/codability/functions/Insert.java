package gr.inf.codability.functions;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import gr.inf.codability.core.Notifications;

public class Insert
{
    // TODO: Work in progress
    public static void insertConstructor( String constructorName )
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

            PsiFile psiFile = DataKeys.PSI_FILE.getData( dataContext );

            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory( project );
            PsiMethod psiMethod = elementFactory.createConstructor( constructorName );
//            ProblemDescriptor.
//            elementFactory.createExpressionFromText(  )
//            RunProfile

        } );
    }

    public static void insertInstanceVar( String varName, PsiType type, String className )
    {
        ApplicationManager.getApplication().invokeLater( () -> {

            Project project = ProjectManager.getInstance().getOpenProjects()[ 0 ];

            if ( project == null )
            {
                Notifications.showWarning( "Insert Instance Variable", "No opened projects have been found" );
                return;
            }

            DataContext dataContext = DataManager.getInstance().getDataContextFromFocus().getResult();
            PsiFile psiFile = DataKeys.PSI_FILE.getData( dataContext );

            if ( psiFile == null )
            {
                Notifications.showWarning( "Insert Instance Variable", "No active PsiFile found" );

                return;
            }

            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory( project );
            JavaPsiFacade facade = JavaPsiFacade.getInstance( project );

            PsiClass psiClass = facade.findClass( className, GlobalSearchScope.fileScope( psiFile ) );

            if ( psiClass == null )
            {
                Notifications.showWarning( "Insert Instance Variable", "Class: " + className + " not found" );

                return;
            }

            new WriteCommandAction.Simple( project )
            {
                @Override
                protected void run() throws Throwable
                {
                    PsiField instanceVar = elementFactory.createField( varName, type );
                    psiClass.add( instanceVar );
                }
            }.execute();

        } );
    }

    public static void insertMain()
    {
        ApplicationManager.getApplication().invokeLater( () -> {

            Project project = ProjectManager.getInstance().getOpenProjects()[ 0 ];

            if ( project == null )
            {
                Notifications.showWarning( "Insert main()", "No opened projects have been found" );
                return;
            }

            DataContext dataContext = DataManager.getInstance().getDataContextFromFocus().getResult();
            PsiFile psiFile = DataKeys.PSI_FILE.getData( dataContext );

            if ( psiFile == null )
            {
                Notifications.showWarning( "Insert main()", "No active PsiFile found" );

                return;
            }

            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory( project );
            JavaPsiFacade facade = JavaPsiFacade.getInstance( project );

            String className = psiFile.getName().replaceFirst( "\\.java", "" );
            PsiClass psiClass = facade.findClass( className, GlobalSearchScope.fileScope( psiFile ) );

            if ( psiClass == null )
            {
                Notifications.showWarning( "Insert main()", "Class: " + className + " not found" );

                return;
            }

            new WriteCommandAction.Simple( project )
            {
                @Override
                protected void run() throws Throwable
                {
                    PsiMethod mainMethod = elementFactory.createMethodFromText( "public static void main( String[] args )\n{\n}\n", psiClass );
                    psiClass.add( mainMethod );
                }
            }.execute();


        } );
    }
}
