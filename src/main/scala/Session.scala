sealed case class Session(
    session_id: Int,
    date: String,
    available_capacity: Int,
    min_age_limit: Int,
    vaccine: String,
    slots: List[String]
)
