package dgroomes.server;

import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.http.netty.channel.ChannelPipelineCustomizer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.compression.CompressionOptions;
import io.netty.handler.codec.http.HttpContentCompressor;
import jakarta.inject.Singleton;

/**
 * Customize the Netty handlers.
 * <p>
 * This pattern is nicely documented in the Micronaut user guide: https://docs.micronaut.io/latest/guide/#nettyPipeline
 */
@Singleton
public class NettyPipelineCustomizer implements BeanCreatedEventListener<ChannelPipelineCustomizer> {

    /**
     * Only compress content with size greater or equal to this many bytes. It's not worth compressing smaller sizes.
     */
    public static final int COMPRESSION_CONTENT_SIZE_THRESHOLD = 1000;

    @Override
    public ChannelPipelineCustomizer onCreated(BeanCreatedEvent<ChannelPipelineCustomizer> event) {
        ChannelPipelineCustomizer customizer = event.getBean();

        if (customizer.isServerChannel()) {
            customizer.doOnConnect(pipeline -> {

                replaceHttpCompressor(pipeline);
                return pipeline;
            });
        }
        return customizer;
    }

    /**
     * Replace the "http-compressor" handler supplied by Micronaut with a very similar compressor.
     * For reference, the exact line where Micronaut adds this handler is https://sourcegraph.com/github.com/micronaut-projects/micronaut-core@1fcfbd95ae7aa98e423b66dfa6b657503c02d075/-/blob/http-server-netty/src/main/java/io/micronaut/http/server/netty/NettyHttpServer.java?L849
     * <p>
     * Why do this? Micronaut uses a deprecated constructor in {@link HttpContentCompressor} which has the effect of
     * missing out on ZSTD compression. This is too bad. Thankfully, Micronaut (and Jetty in this case) are very
     * customizable and extensible, so we can just replace the compressor handler with a new one.
     */
    private void replaceHttpCompressor(ChannelPipeline pipeline) {
        var name = ChannelPipelineCustomizer.HANDLER_HTTP_COMPRESSOR;
        var compressor = new HttpContentCompressor(COMPRESSION_CONTENT_SIZE_THRESHOLD, new CompressionOptions[]{});
        pipeline.replace(name, name, compressor);
    }
}
