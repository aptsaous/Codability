package gr.inf.codability.functions;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import gr.inf.codability.core.Notifications;

public class Open
{
    public static void openClass( String className )
    {
        Project project = ProjectManager.getInstance().getOpenProjects()[ 0 ];

        GlobalSearchScope scope = GlobalSearchScope.allScope( project );
        PsiClass psiClass = JavaPsiFacade.getInstance( project ).findClass( className, scope );

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
    }
}
