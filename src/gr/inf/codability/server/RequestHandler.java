package gr.inf.codability.server;

import com.intellij.openapi.diagnostic.Logger;
import gr.inf.codability.core.Notifications;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.jetbrains.ide.HttpRequestHandler;
import org.jetbrains.io.Responses;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static gr.inf.codability.core.CodabilityRegistration.commandDecoder;
import static gr.inf.codability.core.Notifications.activationCode;
import static gr.inf.codability.core.Notifications.syncNotification;

public class RequestHandler extends HttpRequestHandler
{
    private static final Logger LOG = Logger.getInstance( RequestHandler.class );
    private String authIP = null;

    @Override
    public boolean isAccessible( HttpRequest request )
    {
        return true;
    }

    @Override
    public boolean isSupported( FullHttpRequest request )
    {
        return true;
    }

    @Override
    public boolean process( QueryStringDecoder queryStringDecoder, FullHttpRequest request, ChannelHandlerContext channelHandlerContext ) throws IOException
    {
        Map<String, List<String>> parameters = queryStringDecoder.parameters();

        LOG.info( "Received connection: " + parameters.toString() );

        /* in case the HTTP request doesn't contain any parameters */
        if ( parameters.isEmpty() )
        {
            Responses.send( HttpResponseStatus.BAD_REQUEST, channelHandlerContext.channel() );
            return false;
        }

        String remoteIP = getIP( channelHandlerContext );

        if ( parameters.containsKey( "auth" ) )
        {
            if ( parameters.get( "auth" ).get(0).equals( activationCode ) )
            {
                authIP = remoteIP;
                Notifications.showWarning("Security", "Authorization Successful" );
                syncNotification.expire();

                Responses.send( HttpResponseStatus.OK, channelHandlerContext.channel() );
                return true;
            }
            else
            {
                Notifications.showWarning( "Security", "Invalid activation code sent" );
                Responses.send( HttpResponseStatus.UNAUTHORIZED, channelHandlerContext.channel() );
                return false;
            }
        }


        if ( remoteIP.equals( authIP ) )
        {
            /* Get, analyze and execute cmd */
            if ( parameters.containsKey( "cmd" ) )
            {
                commandDecoder.decode( parameters );
                Responses.send( HttpResponseStatus.OK, channelHandlerContext.channel() );

                return true;
            }

        }


        return false;
    }

    private String getIP( ChannelHandlerContext channelHandlerContext )
    {
        String remoteAddr = channelHandlerContext.channel().remoteAddress().toString();
        int begin = remoteAddr.indexOf( "/" ) + 1;
        int end = remoteAddr.indexOf( ":" );

        return remoteAddr.substring( begin, end );
    }

}
