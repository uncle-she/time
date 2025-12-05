package time.display

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.*
import kotlin.js.Date

/**
 * 时间显示应用的主要类
 */
class TimeDisplayApp {
    private var noSleepEnabled = false
    
    fun start() {
        // 更新时间
        updateTime()
        
        // 每秒更新一次时间
        window.setInterval({ updateTime() }, 1000)
        
        // 添加事件监听器
        addEventListeners()
        
        // 启用 NoSleep
        enableNoSleep()
    }
    
    private fun updateTime() {
        val now = Date()
        val hours = now.getHours().toString().padStart(2, '0')
        val minutes = now.getMinutes().toString().padStart(2, '0')
        
        val dayIndex = now.getDay()
        val daysOfWeekArray = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
        val daysOfWeek = daysOfWeekArray[dayIndex]
        
        val daysOfWeekEnglishArray = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        val daysOfWeekEnglish = daysOfWeekEnglishArray[dayIndex]
        
        val monthsOfYearEnglishArray = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        val monthIndex = now.getMonth()
        val monthsOfYearEnglish = monthsOfYearEnglishArray[monthIndex]
        
        val month = now.getMonth() + 1
        val date = now.getDate()
        
        updateTimeDisplay(hours, minutes)
        updateDateDisplay(month, date, daysOfWeek, daysOfWeekEnglish, monthsOfYearEnglish)
        handleDateColor(hours)
    }
    
    private fun updateTimeDisplay(hours: String, minutes: String) {
        val timeElement = document.getElementById("time") as HTMLElement
        timeElement.textContent = "$hours:$minutes"
    }
    
    private fun updateDateDisplay(
        month: Int, 
        date: Int, 
        daysOfWeek: String, 
        daysOfWeekEnglish: String,
        monthsOfYearEnglish: String
    ) {
        val monthTextElement = document.getElementById("month-text") as HTMLElement
        val dateNumberElement = document.getElementById("date-number") as HTMLElement
        val dayTextElement = document.getElementById("day-text") as HTMLElement
        val monthsOfYearEnglishElement = document.getElementById("months-of-year-english") as HTMLElement
        val daysOfWeekChineseElement = document.getElementById("days-of-week-chinese") as HTMLElement
        val daysOfWeekEnglishElement = document.getElementById("days-of-week-english") as HTMLElement
        
        monthTextElement.textContent = "$month 月 "
        dateNumberElement.textContent = "$date"
        dayTextElement.textContent = " 日"
        monthsOfYearEnglishElement.textContent = monthsOfYearEnglish
        daysOfWeekChineseElement.textContent = daysOfWeek
        daysOfWeekEnglishElement.textContent = daysOfWeekEnglish
    }
    
    private fun handleDateColor(hours: Int) {
        val dateNumberElement = document.getElementById("date-number") as HTMLElement
        
        if (hours >= 12 && hours <= 23) {
            // 如果没有被点击过，则自动设为红色
            if (!dateNumberElement.classList.contains("clicked")) {
                dateNumberElement.style.color = "red"
            }
            // 如果已被点击过，则保持用户选择的颜色状态
        } else {
            // 午夜后重置颜色和点击状态
            dateNumberElement.style.color = "rgb(200, 200, 200)"
            dateNumberElement.classList.remove("clicked")
            dateNumberElement.classList.remove("manual-red") // 清除手动红色状态
        }
    }
    
    private fun addEventListeners() {
        val dateElement = document.getElementById("date") as HTMLElement
        val daysOfWeekElement = document.getElementById("days-of-week") as HTMLElement
        val dateNumberElement = document.getElementById("date-number") as HTMLElement
        
        dateElement.addEventListener("click", {
            toggleDateColor(dateNumberElement)
            dateNumberElement.classList.add("clicked")
        })
        
        daysOfWeekElement.addEventListener("click", {
            toggleDateColor(dateNumberElement)
            dateNumberElement.classList.add("clicked")
        })
    }
    
    private fun toggleDateColor(dateNumberElement: HTMLElement) {
        val currentColor = dateNumberElement.style.color
        
        // 切换颜色：如果当前是普通颜色则变为红色，否则变为普通颜色
        if (currentColor == "" || currentColor == "rgb(200, 200, 200)" || currentColor == "white") {
            dateNumberElement.style.color = "red"
            dateNumberElement.classList.add("manual-red")
        } else {
            dateNumberElement.style.color = "rgb(200, 200, 200)"
            dateNumberElement.classList.remove("manual-red")
        }
    }
    
    private fun enableNoSleep() {
        document.addEventListener("click", { event ->
            if (!noSleepEnabled) {
                // 在实际项目中，你需要正确初始化 NoSleep
                // 这里只是保留原始功能的概念
                noSleepEnabled = true
            }
        })
    }
}

fun main() {
    val app = TimeDisplayApp()
    app.start()
}