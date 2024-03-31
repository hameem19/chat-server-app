    package com.cyberchat.chatserver.controller;

    import com.cyberchat.chatserver.model.ChatMessageDTO;
    import com.cyberchat.chatserver.service.ChatMessageService;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.validation.Valid;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Positive;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.Authentication;
    import org.springframework.validation.annotation.Validated;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.Parameter;
    import io.swagger.v3.oas.annotations.media.Content;
    import io.swagger.v3.oas.annotations.media.Schema;
    import io.swagger.v3.oas.annotations.responses.ApiResponse;
    import io.swagger.v3.oas.annotations.responses.ApiResponses;

    @RestController
    @RequestMapping("/api/messages")
    @Slf4j
    @Tag(name = "Chat Message API", description = "Operations related to chat messages in a chat application")
    @Validated
    public class ChatMessageController {

        private final ChatMessageService chatMessageService;

        public ChatMessageController(ChatMessageService chatMessageService) {
            this.chatMessageService = chatMessageService;
        }

        @PostMapping("/{chatRoomId}")
        @Operation(summary = "Send a message to a chat room", description = "Sends a new message to the specified chat room")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Successfully sent the message", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChatMessageDTO.class))),
                @ApiResponse(responseCode = "400", description = "Invalid input parameters", content = @Content),
                @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
                @ApiResponse(responseCode = "404", description = "Chat room not found", content = @Content)
        })
        public ResponseEntity<ChatMessageDTO> sendMessage(
                @Parameter(description = "ID of the chat room", required = true) @PathVariable @NotNull @Positive Long chatRoomId,
                @Parameter(description = "Content of the message", required = true) @RequestBody @NotBlank @Valid String messageContent,
                Authentication authentication) {
            String username = authentication.getName();
            ChatMessageDTO message = chatMessageService.sendMessage(chatRoomId, messageContent, username);
            log.info("Response :: " + message);
            return ResponseEntity.ok(message);
        }

        @GetMapping("/{chatRoomId}")
        @Operation(summary = "Get messages for a chat room", description = "Retrieves all messages for the specified chat room")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved messages", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChatMessageDTO.class))),
                @ApiResponse(responseCode = "400", description = "Invalid input parameters", content = @Content),
                @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
                @ApiResponse(responseCode = "404", description = "Chat room not found", content = @Content)
        })
        public ResponseEntity<List<ChatMessageDTO>> getMessagesForChatRoom(
                @Parameter(description = "ID of the chat room", required = true) @PathVariable @NotNull @Positive Long chatRoomId,
                Authentication authentication) {
            List<ChatMessageDTO> messages = chatMessageService.getMessagesForChatRoom(chatRoomId);
            return ResponseEntity.ok(messages);
        }

        @DeleteMapping("/{messageId}")
        @Operation(summary = "Delete a message", description = "Deletes the specified message")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "204", description = "Successfully deleted the message", content = @Content),
                @ApiResponse(responseCode = "400", description = "Invalid input parameters", content = @Content),
                @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
                @ApiResponse(responseCode = "404", description = "Message not found", content = @Content)
        })
        public ResponseEntity<Void> deleteMessage(
                @Parameter(description = "ID of the message", required = true) @PathVariable @NotNull @Positive Long messageId) {
            chatMessageService.deleteMessage(messageId);
            return ResponseEntity.noContent().build();
        }
    }