package gr.inf.codability.functions;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.search.GlobalSearchScope;
import gr.inf.codability.core.Notifications;

public class Open
{
    public static void openClass( String className )
    {
        ApplicationManager.getApplication().invokeLater( () -> {

            Project project = ProjectManager.getInstance().getOpenProjects()[ 0 ];

            if ( project == null )
            {
                Notifications.showWarning( "Open Class", "No opened projects have been found" );
                return;
            }

            GlobalSearchScope scope = GlobalSearchScope.allScope( project );
            PsiClass psiClass = JavaPsiFacade.getInstance( project ).findClass( className, scope );

            psiClass.getContainingFile();

            if ( psiClass != null )
            {
                VirtualFile vf = psiClass.getContainingFile().getVirtualFile();

                new OpenFileDescriptor( project, vf, 1, 0 ).navigateInEditor( project, true );

                Notifications.showInfo( "Open Class", "Opened Class: " + className );

            }
            else
            {
                Notifications.showWarning( "Open Class", "Class: " + className + " not found!" );
            }

        });





    }
}
