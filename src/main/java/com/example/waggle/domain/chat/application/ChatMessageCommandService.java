package com.example.waggle.domain.chat.application;

import com.example.waggle.domain.chat.presentation.dto.ChatMessageDto;

public interface ChatMessageCommandService {

    String createChatMessage(ChatMessageDto chatMessageDto);

}
