package gr.inf.codability.functions;

import com.intellij.ide.highlighter.ModuleFileType;
import com.intellij.ide.util.projectWizard.JavaModuleBuilder;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ApplicationNamesInfo;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.module.ModuleTypeManager;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.roots.CompilerProjectExtension;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VfsUtilCore;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiManager;
import com.intellij.util.SystemProperties;
import gr.inf.codability.core.Notifications;

import java.io.File;

public class Create
{
    public static void createClass( String className )
    {
        ApplicationManager.getApplication().invokeLater( () -> {
            Project project = ProjectManager.getInstance().getOpenProjects()[ 0 ];

            if ( project == null )
            {
                Notifications.showWarning( "Create Class", "No opened projects have been found" );
                return;
            }

            VirtualFile projectDir = project.getBaseDir();
            VirtualFile srcDir = projectDir.findChild( "src" );

            ApplicationManager.getApplication().runWriteAction( () -> {

                PsiDirectory directory = PsiManager.getInstance( project ).findDirectory( srcDir );
                JavaDirectoryService.getInstance().createClass( directory, className );

            } );

        } );

    }

    public static void createProject( String projectName )
    {
        ApplicationManager.getApplication().invokeLater( () -> {

            final String userHome = SystemProperties.getUserHome();
            String ideName = ApplicationNamesInfo.getInstance().getLowercaseProductName();
            String path = userHome.replace( '/', File.separatorChar ) + File.separator + ideName.replace( " ", "" ) + "Projects";

            File projectDir = new File( path + File.separatorChar + projectName );
            File srcDir = new File( path + File.separatorChar + projectName + File.separatorChar + "src" );
            File outDir = new File( path + File.separatorChar + projectName + File.separatorChar + "out" );

            projectDir.mkdir();
            srcDir.mkdir();
            outDir.mkdir();

            ApplicationManager.getApplication().runWriteAction( () -> {

                Project project = ProjectManager.getInstance().createProject( projectName, projectDir.getAbsolutePath() );

                final String projectPath = projectDir.getAbsolutePath();
                String imlName = projectPath + File.separatorChar + projectName + ModuleFileType.DOT_DEFAULT_EXTENSION;

                final Module module = ModuleManager.getInstance( project ).newModule( imlName, ModuleTypeManager.getInstance().getDefaultModuleType().getId() );
                JavaModuleBuilder javaModuleBuilder = new JavaModuleBuilder();
                ModuleRootManager rootManager = ModuleRootManager.getInstance( module );
                ModifiableRootModel rootModel = rootManager.getModifiableModel();

                rootModel.setSdk( ProjectJdkTable.getInstance().getAllJdks()[0] );
                ProjectRootManager.getInstance( project ).setProjectSdk( ProjectJdkTable.getInstance().getAllJdks()[0] );

                if ( rootModel.getContentRoots().length == 0 )
                    rootModel.addContentEntry( projectPath );

                CompilerProjectExtension.getInstance( project ).setCompilerOutputUrl( VfsUtilCore.pathToUrl( outDir.getAbsolutePath() ) );

                try
                {
                    javaModuleBuilder.setupRootModel( rootModel );
                    javaModuleBuilder.setCompilerOutputPath( outDir.getAbsolutePath() );
                    javaModuleBuilder.commit( project );
                }
                catch ( ConfigurationException e )
                {
                    e.printStackTrace();
                }

                project.save();
            } );
        } );
    }
}
