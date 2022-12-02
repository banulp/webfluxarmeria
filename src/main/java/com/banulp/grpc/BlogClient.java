package com.banulp.grpc;


import com.linecorp.armeria.client.grpc.GrpcClients;
import com.linecorp.armeria.client.logging.LoggingClient;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BlogClient {
    private static final Logger logger = LoggerFactory.getLogger(BlogClient.class);
    static com.banulp.grpc.proto.BlogServiceGrpc.BlogServiceBlockingStub client;

    public static void main(String[] args) throws Exception {
//        client = GrpcClients.newClient("http://127.0.0.1:8080/",
//                BlogServiceGrpc.BlogServiceBlockingStub.class);

        ////////////

//        ClientBuilder cb = Clients.builder("http");
//        cb.decorator((delegate, ctx, req) -> {
//            final AtomicBoolean initialHttpData = new AtomicBoolean(true);
//
//            return delegate
//                    .execute(ctx, req)
//                    .mapHeaders(headers -> headers.toBuilder().add("x-foo", "foobar").build())
//                    .mapData((httpData) -> {
//                HttpData result = httpData;
//                if (initialHttpData.get()) {
//                    initialHttpData.set(false);
//                    byte[] ascii = "{\"foo\": \"foobar\",".getBytes(StandardCharsets.US_ASCII);
//                    byte[] combined = Arrays.copyOf(ascii, ascii.length + httpData.length() - 1);
//                    System.arraycopy(httpData.array(), 1, combined, ascii.length, httpData.length());
//                    result = HttpData.wrap(combined);
//                }
//                return result;
//            });
//        });

        ////////////


        client = GrpcClients.builder("http://127.0.0.1:8080/")
                .decorator(LoggingClient.newDecorator())  // add this
                .build(com.banulp.grpc.proto.BlogServiceGrpc.BlogServiceBlockingStub.class);

        BlogClient blogClient = new BlogClient();

        // post
        blogClient.createBlogPost("banulp", "is smart.");
        blogClient.createBlogPost("banulp", "is kind.");
        blogClient.createBlogPost("banulp", "is holly.");
        blogClient.createBlogPost("banulp", "is good.");
        blogClient.createBlogPost("banulp", "is fair.");

        // get 1
        blogClient.getBlogPost(1);

//        // list
//        blogClient.listBlogPosts();
//
//        // update
//        blogClient.updateBlogPost(1, "THE GREAT BANULP", "IS WONDERFUL.");
//        blogClient.getBlogPost(1);
//
//        // make exception
//        blogClient.updateBlogPost(100, "THE GREAT BANULP", "IS WONDERFUL.");
//        blogClient.getBlogPost(100);
//
//        // delete
//        blogClient.deleteBlogPost(2);
//        blogClient.listBlogPosts();
    }

    void createBlogPost(String title, String content) {
        final com.banulp.grpc.proto.CreateBlogPostRequest request =
                com.banulp.grpc.proto.CreateBlogPostRequest.newBuilder()
                        .setTitle(title)
                        .setContent(content)
                        .build();
        final com.banulp.grpc.proto.BlogPost response = client.createBlogPost(request);
        logger.info("[Create response] Title: {} Content: {}",
                response.getTitle(), response.getContent());
    }

    void getBlogPost(int id){
        final com.banulp.grpc.proto.BlogPost blogPost = client.getBlogPost(com.banulp.grpc.proto.GetBlogPostRequest.newBuilder().setId(id).build());
        logger.info("[getBlogPost]  Title: {} Content: {}", blogPost.getTitle(), blogPost.getContent());
    }

    void listBlogPosts() {
        final com.banulp.grpc.proto.ListBlogPostsResponse
                response = client.listBlogPosts(com.banulp.grpc.proto.ListBlogPostsRequest.newBuilder().setDescending(false).build());

        final List<com.banulp.grpc.proto.BlogPost> blogs = response.getBlogsList();

        logger.info("[listBlogPosts] blogs: {}", blogs.size());

    }

    void updateBlogPost(Integer id, String newTitle, String newContent){
        final com.banulp.grpc.proto.UpdateBlogPostRequest request = com.banulp.grpc.proto.UpdateBlogPostRequest.newBuilder()
                .setId(id)
                .setTitle(newTitle)
                .setContent(newContent)
                .build();
        final com.banulp.grpc.proto.BlogPost updated = client.updateBlogPost(request);

        logger.info("[updateBlogPost] updated: {}", updated.getTitle(), updated.getContent());
    }

    void deleteBlogPost(int id) {
        final com.banulp.grpc.proto.DeleteBlogPostRequest request = com.banulp.grpc.proto.DeleteBlogPostRequest.newBuilder().setId(id).build();
        try {
            client.deleteBlogPost(request);
        } catch (StatusRuntimeException statusException) {
            // Handle exception
        }
    }
}
