case class VaccineCenter(
    center_id: Int,
    name: String,
    district_name: String,
    pincode: String,
    fee_type: String,
    from: String,
    to: String,
    session: List[Session]
)
