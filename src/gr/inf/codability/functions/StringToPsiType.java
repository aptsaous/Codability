package gr.inf.codability.functions;

import com.intellij.psi.PsiType;

public class StringToPsiType
{
    public static PsiType getPsiType( String type )
    {
        switch ( type )
        {
            case "integer":
                return PsiType.INT;
            case "double":
                return PsiType.DOUBLE;
            case "boolean":
                return PsiType.BOOLEAN;
            case "character":
            case "char":
                return PsiType.CHAR;
            case "byte":
                return PsiType.BYTE;
            case "float":
                return PsiType.FLOAT;
        }

        return null;
    }
}
