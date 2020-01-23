package taskscheduler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import taskscheduler.domain.DataTableRequest;
import taskscheduler.domain.DataTableResult;
import taskscheduler.service.TaskService;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private DataTableRequest dataTableRequest;
    private DataTableResult dataTableResult;

    @Before
    public void setup() {

        dataTableResult = new DataTableResult();
        dataTableResult.setDraw(1);
        dataTableResult.setRecordsTotal(5);
        dataTableResult.setRecordsFiltered(0);
        dataTableResult.setData(new Object[1][9]);
        dataTableResult.getData()[0][0] = "test";
        dataTableResult.getData()[0][1] = "test";
        dataTableResult.getData()[0][2] = "test";
        dataTableResult.getData()[0][3] = "test";
        dataTableResult.getData()[0][4] = "test";
        dataTableResult.getData()[0][5] = "test";
        dataTableResult.getData()[0][6] = "test";
        dataTableResult.getData()[0][7] = "test";
        dataTableResult.getData()[0][8] = "test";

        dataTableRequest = new DataTableRequest();
        dataTableRequest.setDraw(1);
        dataTableRequest.setStart(0);
        dataTableRequest.setLength(5);
        dataTableRequest.setSortColumn(0);
        dataTableRequest.setSortDirection("asc");
        dataTableRequest.setFilter("");
    }

    @Test
    public void testGetTaskList() throws Exception {

        Mockito.when(taskService.getTaskList(dataTableRequest)).thenReturn(dataTableResult);

        mockMvc.perform(get("/list")
                .contentType(MediaType.APPLICATION_JSON)
                .param("draw", "1")
                .param("start", "0")
                .param("length", "5")
                .param("order[0][column]", "0")
                .param("order[0][dir]", "asc")
                .param("search[value]", "")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.draw").value(1))
                .andExpect(jsonPath("$.recordsTotal").value(5))
                .andExpect(jsonPath("$.recordsFiltered").value(0))
                .andExpect(jsonPath("$.data[0][0]").value("test"))
                .andExpect(jsonPath("$.data[0][1]").value("test"))
                .andExpect(jsonPath("$.data[0][2]").value("test"))
                .andExpect(jsonPath("$.data[0][3]").value("test"))
                .andExpect(jsonPath("$.data[0][4]").value("test"))
                .andExpect(jsonPath("$.data[0][5]").value("test"))
                .andExpect(jsonPath("$.data[0][6]").value("test"))
                .andExpect(jsonPath("$.data[0][7]").value("test"))
                .andExpect(jsonPath("$.data[0][8]").value("test"));

        verify(taskService, times(1)).getTaskList(dataTableRequest);
        Mockito.verifyNoMoreInteractions(taskService);
    }
}
