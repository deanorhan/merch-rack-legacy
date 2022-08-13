package org.daemio.merch.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.daemio.merch.domain.Merch;
import org.daemio.merch.error.MerchNotFoundProblem;
import org.daemio.merch.mapper.MerchMapper;
import org.daemio.merch.model.MerchPage;
import org.daemio.merch.repository.MerchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
@DisplayName("Merch service tests")
public class MerchServiceTest {

    @Mock
    private MerchRepository merchRepository;
    @Spy
    private MerchMapper merchMapper = Mappers.getMapper(MerchMapper.class);
    @Mock
    private Page<Merch> page;

    @InjectMocks
    private MerchService service;

    @DisplayName("when calling for a list of merch, then should return a list")
    @Test
    public void whenGettingList_thenReturnList() {
        List <Merch> expectedResult = Arrays.asList(new Merch());
        when(merchRepository.findAll()).thenReturn(expectedResult);

        List<Merch> actualResult = service.getMerchList();

        assertNotNull(actualResult, "Merch list is null");
        assertNotEquals(0, actualResult.size(), "Merch list is empty");
        assertArrayEquals(actualResult.toArray(), expectedResult.toArray(),
            "Merch list is not what was expected");
    }

    @Test
    public void whenGettingPagedList_theReturnList() {
        var merchList = Arrays.asList(new Merch());
        var expectedResult = new MerchPage();
        expectedResult.setMerch(merchList);

        when(page.getContent()).thenReturn(merchList);
        when(merchRepository.findAll(any(PageRequest.class))).thenReturn(page);

        var actualResult = service.getMerchPage(PageRequest.of(0, 1));

        assertNotNull(actualResult, "Merch list is null");
        assertNotEquals(0, actualResult.getMerch().size(), "Merch list is empty");
        assertArrayEquals(actualResult.getMerch().toArray(), expectedResult.getMerch().toArray(),
            "Merch list is not what was expected");
    }

    @DisplayName("given some merch id and a merch item exists with that id, " +
        "when calling for merch with the given id, then return that specific merch item")
    @Test
    public void whenGettingSpecificMerch_thenReturnMerchItem() {
        var merchId = 5;
        Merch expectedResult = new Merch();
        expectedResult.setId(merchId);

        when(merchRepository.findById(merchId)).thenReturn(Optional.of(expectedResult));

        Merch actualResult = service.getMerch(merchId);

        assertNotNull(actualResult, "Merch item is null");
        assertEquals(expectedResult.getId(), actualResult.getId(), "Ids of the merch do not match");
    }

    @DisplayName("given some merch id and the merch item does not exist, when calling for " +
        "the merch item then throw the merch not found exception")
    @Test
    public void givenMerchNotThere_whenGettingMerchItem_thenThrowNotFoundException() {
        when(merchRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(MerchNotFoundProblem.class,
            () -> service.getMerch(1), "Exception not thrown when no merch");
    }
}
