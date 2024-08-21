package com.example.diary.service;

import com.example.diary.dto.BoardDTO;
import com.example.diary.entity.BoardEntity;
import com.example.diary.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//데이터 전송 관리
//entity와 dto를 주고받는 역할
// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // dto->entity 해서 repository에 저장
    public void save(BoardDTO boardDTO) throws IOException {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO); // dto->entity
        boardRepository.save(boardEntity); // repository에 저장

    }

    // Entity리스트를 DTO리스트로 변환한다.
    @Transactional //하나의 트랜잭션에서 작동하게 하는 애노테이션. 원자성을 띈다(실패하면 롤백)
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll(); //데이터베이스에 있는 모든 엔티티를 리스트 형태로 반환
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity: boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity)); //조회된 각각의 BoardEntity 객체를 BoardDTO로 변환해서 리스트 형태로 만듦
        }
        return boardDTOList;
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            // entity를 dto로 변환.
            // 여기서 boardFileEntity도 접근하니까 @Transactional 이노테이션이 필요함.
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        //update메소드가 따로 내장되지 않음. save메소드로 update도 하고 insert도 함
        //insert는 id없고 update는 id있음
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId()); //코드 겹쳐서 그냥 이거 이용
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //0부터 시작해서
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작
        // findall은 리스트객체지만 얘는 page객체. 그래야 메소드 사용가능
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // 목록: id, writer, title, hits, createdTime
        //map 메소드로 entity를 BoardDTO로 바꿔줌
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardDTOS;
    }
}