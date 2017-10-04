package gr.inf.codability.functions;

import com.intellij.codeInsight.generation.GenerateMembersUtil;
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
import com.intellij.psi.util.PsiTreeUtil;
import gr.inf.codability.core.Notifications;

public class Insert
{
    public static void insertDefaultConstructor()
    {
        ApplicationManager.getApplication().invokeLater( () -> {

            Project project = ProjectManager.getInstance().getOpenProjects()[ 0 ];

            if ( project == null )
            {
                Notifications.showWarning( "Insert Constructor", "No opened projects have been found" );
                return;
            }

            DataContext dataContext = DataManager.getInstance().getDataContextFromFocus().getResult();
            PsiFile psiFile = DataKeys.PSI_FILE.getData( dataContext );

            if ( psiFile == null )
            {
                Notifications.showWarning( "Insert Constructor", "No active PsiFile found" );

                return;
            }

            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory( project );

            String className = psiFile.getName().replaceFirst( "\\.java", "" );
            PsiClass psiClass = JavaPsiFacade.getInstance( project ).findClass( className, GlobalSearchScope.fileScope( psiFile ) );

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
                    PsiMethod psiMethod = elementFactory.createConstructor();
                    psiClass.add( psiMethod );
                }
            }.execute();

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

    // TODO: Work in progress
    public static void insertExpression()
    {
        ApplicationManager.getApplication().invokeLater( () -> {

            Project project = ProjectManager.getInstance().getOpenProjects()[ 0 ];

            if ( project == null )
            {
                Notifications.showWarning( "Insert expression", "No opened projects have been found" );
                return;
            }

            Editor editor = FileEditorManager.getInstance( project ).getSelectedTextEditor();

            if ( editor == null )
            {
                Notifications.showWarning( "Insert expression", "No active editor has been found" );
                return;
            }

            DataContext dataContext = DataManager.getInstance().getDataContextFromFocus().getResult();
            PsiFile psiFile = DataKeys.PSI_FILE.getData( dataContext );

            if ( psiFile == null )
            {
                Notifications.showWarning( "Insert expression", "No active PsiFile found" );

                return;
            }

            int offset = editor.getCaretModel().getOffset();

            PsiElement psiElement = psiFile.findElementAt( offset );
            PsiMethod psiMethod = PsiTreeUtil.getParentOfType( psiElement, PsiMethod.class );

            if ( psiMethod == null )
            {
                Notifications.showWarning( "Insert expression", "Caret not positioned inside a function/method" );

                return;
            }

            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory( project );

            new WriteCommandAction.Simple( project )
            {
                @Override
                protected void run() throws Throwable
                {
                    PsiAssignmentExpression expression = (PsiAssignmentExpression) elementFactory.createExpressionFromText( "a = b", null );
                    PsiStatement c = elementFactory.createStatementFromText(";", null );
                    expression.add( c );
//                    GenerateMembersUtil.generateSetterPrototype(  )
//                    expression.
                    PsiDocumentManager.getInstance( project ).getDocument( psiFile ).insertString( offset, "int a = 0;\n" );
                    System.out.println("Hi");
                }
            }.execute();

        } );
    }

    public static void insertGetterFunction( String varName )
    {
        ApplicationManager.getApplication().invokeLater( () -> {

            Project project = ProjectManager.getInstance().getOpenProjects()[ 0 ];

            if ( project == null )
            {
                Notifications.showWarning( "Insert getter function", "No opened projects have been found" );
                return;
            }

            Editor editor = FileEditorManager.getInstance( project ).getSelectedTextEditor();

            if ( editor == null )
            {
                Notifications.showWarning( "Insert getter function", "No active editor has been found" );
                return;
            }

            DataContext dataContext = DataManager.getInstance().getDataContextFromFocus().getResult();
            PsiFile psiFile = DataKeys.PSI_FILE.getData( dataContext );

            if ( psiFile == null )
            {
                Notifications.showWarning( "Insert getter function", "No active PsiFile found" );

                return;
            }

            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory( project );
            JavaPsiFacade facade = JavaPsiFacade.getInstance( project );

            String className = psiFile.getName().replaceFirst( "\\.java", "" );
            PsiClass psiClass = facade.findClass( className, GlobalSearchScope.fileScope( psiFile ) );

            if ( psiClass == null )
            {
                Notifications.showWarning( "Insert getter function", "Class: " + className + " not found" );

                return;
            }

            PsiField[] psiFields = psiClass.getAllFields();
            PsiField instanceVar = null;

            for ( PsiField psiField : psiFields )
            {
                if ( psiField.getName().equals( varName ) )
                {
                    instanceVar = psiField;
                    break;
                }
            }

            if ( instanceVar == null )
            {
                Notifications.showWarning( "Insert getter function", "Instance variable: " + varName + " not found" );
                return;
            }

            PsiField finalInstanceVar = instanceVar;

            new WriteCommandAction.Simple( project )
            {
                @Override
                protected void run() throws Throwable
                {
                    PsiMethod psiMethod = GenerateMembersUtil.generateGetterPrototype( finalInstanceVar );
                    psiClass.add( psiMethod );
                }
            }.execute();


        } );
    }

    public static void insertSetterFunction( String varName )
    {
        ApplicationManager.getApplication().invokeLater( () -> {

            Project project = ProjectManager.getInstance().getOpenProjects()[ 0 ];

            if ( project == null )
            {
                Notifications.showWarning( "Insert setter function", "No opened projects have been found" );
                return;
            }

            Editor editor = FileEditorManager.getInstance( project ).getSelectedTextEditor();

            if ( editor == null )
            {
                Notifications.showWarning( "Insert setter function", "No active editor has been found" );
                return;
            }

            DataContext dataContext = DataManager.getInstance().getDataContextFromFocus().getResult();
            PsiFile psiFile = DataKeys.PSI_FILE.getData( dataContext );

            if ( psiFile == null )
            {
                Notifications.showWarning( "Insert setter function", "No active PsiFile found" );

                return;
            }

            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory( project );
            JavaPsiFacade facade = JavaPsiFacade.getInstance( project );

            String className = psiFile.getName().replaceFirst( "\\.java", "" );
            PsiClass psiClass = facade.findClass( className, GlobalSearchScope.fileScope( psiFile ) );

            if ( psiClass == null )
            {
                Notifications.showWarning( "Insert setter function", "Class: " + className + " not found" );

                return;
            }

            PsiField[] psiFields = psiClass.getAllFields();
            PsiField instanceVar = null;

            for ( PsiField psiField : psiFields )
            {
                if ( psiField.getName().equals( varName ) )
                {
                    instanceVar = psiField;
                    break;
                }
            }

            if ( instanceVar == null )
            {
                Notifications.showWarning( "Insert setter function", "Instance variable: " + varName + " not found" );
                return;
            }

            PsiField finalInstanceVar = instanceVar;

            new WriteCommandAction.Simple( project )
            {
                @Override
                protected void run() throws Throwable
                {
                    PsiMethod psiMethod = GenerateMembersUtil.generateSetterPrototype( finalInstanceVar );
                    psiClass.add( psiMethod );
                }
            }.execute();


        } );
    }

    public static void insertPrintFunction( String text )
    {
        ApplicationManager.getApplication().invokeLater( () -> {

            Project project = ProjectManager.getInstance().getOpenProjects()[ 0 ];

            if ( project == null )
            {
                Notifications.showWarning( "Print", "No opened projects have been found" );
                return;
            }

            Editor editor = FileEditorManager.getInstance( project ).getSelectedTextEditor();

            if ( editor == null )
            {
                Notifications.showWarning( "Print", "No active editor has been found" );
                return;
            }

            DataContext dataContext = DataManager.getInstance().getDataContextFromFocus().getResult();
            PsiFile psiFile = DataKeys.PSI_FILE.getData( dataContext );

            if ( psiFile == null )
            {
                Notifications.showWarning( "Print", "No active PsiFile found" );

                return;
            }

            int offset = editor.getCaretModel().getOffset();

            PsiElement psiElement = psiFile.findElementAt( offset );
            PsiMethod psiMethod = PsiTreeUtil.getParentOfType( psiElement, PsiMethod.class );

            if ( psiMethod == null )
            {
                Notifications.showWarning( "Print", "Caret not positioned inside a function/method" );

                return;
            }

            new WriteCommandAction.Simple( project ) {
                @Override
                protected void run() throws Throwable
                {
                    PsiStatement statement = JavaPsiFacade.getElementFactory( project ).createStatementFromText( "System.out.println( \"" + text + "\" );", null );
                    psiMethod.getBody().add( statement );
                }
            }.execute();

        } );
    }
}
